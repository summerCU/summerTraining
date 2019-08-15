package sortUserRating;

import java.io.IOException;
import java.util.Iterator;

import org.apache.hadoop.classification.InterfaceAudience.Public;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.FloatWritable;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.io.WritableComparable;
import org.apache.hadoop.io.WritableComparator;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.util.GenericOptionsParser;

public class SortByValue {
	public SortByValue(){}
/*	
    public static void main(String[] args) throws Exception {
        Configuration conf = new Configuration();
        String[] otherArgs = (new GenericOptionsParser(conf, args)).getRemainingArgs();
        if(otherArgs.length < 2) {
            System.err.println("Usage: sort user ratinng <in> [<in>...] <out>");
            System.exit(2);
        }
        
        Job jobsort = Job.getInstance(conf, "Sort user rating");
        jobsort.setJarByClass(SortByValue.class);
        
        jobsort.setMapperClass(SortByValue.ReverseMapper.class);
        //jobsort.setCombinerClass(SortByValue.SortValueReducer.class);
        jobsort.setReducerClass(SortByValue.SortValueReducer.class);
        
        jobsort.setMapOutputKeyClass(IntWritable.class);
        jobsort.setMapOutputValueClass(Text.class);
        
        jobsort.setOutputKeyClass(Text.class);
        jobsort.setOutputValueClass(IntWritable.class);
        //定义input和output参数
        FileInputFormat.addInputPath(jobsort, new Path(otherArgs[0]));
        FileOutputFormat.setOutputPath(jobsort, new Path(otherArgs[otherArgs.length - 1]));
        
        System.exit(jobsort.waitForCompletion(true)?0:1);
    }
*/	
	
	
	//按IntWritable降序排序的类
		public static class DescSort extends WritableComparator{
			public DescSort(){
				super(IntWritable.class,true);		//注册排序组件
			}
			
			@Override
			public int compare(byte[] arg0,int arg1,int arg2,byte[] arg3,int arg4,int arg5){
				return -super.compare(arg0, arg1, arg2, arg3, arg4, arg5);	//注意使用负号来完成降序
			}
			
			@Override
			public int compare(Object a,Object b){
				return -super.compare(a, b);		//注意使用负号来完成降序  
			}
			
		}


	
    public static class ReverseMapper extends Mapper<Object, Text, IntWritable, Text> {
        private Text user = new Text();
        private IntWritable Times = new IntWritable();
        public ReverseMapper() {
        }
     
        public void map(Object key, Text value,Context context) throws IOException, InterruptedException {
        	String itr = new String(value.toString());
        	if(!(itr.isEmpty())){
        		String[] row = itr.split("\\s+");
        		String userid = new String(row[0]);
        		int times = Integer.parseInt(row[1]);
        		this.user.set(userid);
        		this.Times.set(times);
        		context.write(this.Times,this.user); 
        	}
        }
    }
 
    public static class SortValueReducer extends Reducer<IntWritable, Text, Text, IntWritable> {

        public SortValueReducer() {}
        @Override
        public void reduce(IntWritable key, Iterable<Text> values,Context context) throws IOException, InterruptedException {
        	 for (Text i : values){
        		//String xx = new String(i.get());
        		 context.write(i, key);
        	 }
        	
        }
    }
}
