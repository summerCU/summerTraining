package classifyMovie;

import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.util.GenericOptionsParser;



public class ClassifyMovie {

	public ClassifyMovie(){}

    public static void main(String[] args) throws Exception {
        Configuration conf = new Configuration();
        String[] otherArgs = (new GenericOptionsParser(conf, args)).getRemainingArgs();
        if(otherArgs.length < 2) {
            System.err.println("Classify movie by gener <in> [<in>...] <out>");
            System.exit(2);
        }
        
        Job jobClassify = Job.getInstance(conf, "Classify movie by gener");
        jobClassify.setJarByClass(ClassifyMovie.class);
        
        jobClassify.setMapperClass(ClassifyMovie.CutGenersMapper.class);
        jobClassify.setReducerClass(ClassifyMovie.CutMovieReducer.class);
        
        jobClassify.setMapOutputKeyClass(Text.class);
        jobClassify.setMapOutputValueClass(Text.class);
        
        jobClassify.setOutputKeyClass(Text.class);
        jobClassify.setOutputValueClass(Text.class);
        //定义input和output参数
        FileInputFormat.addInputPath(jobClassify, new Path(otherArgs[0]));
        FileOutputFormat.setOutputPath(jobClassify, new Path(otherArgs[otherArgs.length - 1]));
        
        System.exit(jobClassify.waitForCompletion(true)?0:1);
    }

    public static class CutGenersMapper extends Mapper<Object, Text, Text, Text> {
        private Text Title = new Text();
        private Text Gener = new Text();
        public CutGenersMapper() {
        }
     
        public void map(Object key, Text value,Context context) throws IOException, InterruptedException {
        	String itr = new String(value.toString());
        	String regex="movieId,//S+";//正则表达式
        	Pattern p=Pattern.compile(regex);
        	Matcher m=p.matcher(itr);	//去掉表头
        	if(!(m.matches())){
//        	if(Integer.parseInt(key.toString())!=0){
        		String[] row = itr.split(",");
        		String mtitle = new String(row[1]);
        		//有的电影名中包含“，”将电影名字补全
        		for(int j=2;j<row.length-1;j++){
        			mtitle += ","+row[j];
        		}
        		//电影类别取row中最后一个元素
        		String mgeners = new String(row[row.length-1]);
        		this.Title.set(mtitle);
        		String[] geners = mgeners.split("\\|");	//将电影类别按照“|”进行划分，取得每个类别
        		for(int i=0;i<geners.length;i++){
        			String gener = new String(geners[i]);
        			//System.out.println(gener);
        			this.Gener.set(gener);
        			context.write(this.Gener,this.Title);	//颠倒key和value。 
        		}
        	}
        }
    }
 
    public static class CutMovieReducer extends Reducer<Text, Text, Text, Text> {

        public CutMovieReducer() {}
        @Override
        public void reduce(Text key, Iterable<Text> values,Context context) throws IOException, InterruptedException {
        	 for (Text gener : values){
        		//String xx = new String(i.get());
        		 context.write(key,gener);
        	 }
        	
        }
    }

}
