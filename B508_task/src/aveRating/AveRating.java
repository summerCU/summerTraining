package aveRating;

import java.io.IOException;
import java.util.Iterator;
import java.util.StringTokenizer;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.FloatWritable;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.util.GenericOptionsParser;

import junit.framework.Test;

public class AveRating {

    public AveRating() {
    }
 
    public static void main(String[] args) throws Exception {
        Configuration conf = new Configuration();
        String[] otherArgs = (new GenericOptionsParser(conf, args)).getRemainingArgs();
        if(otherArgs.length < 2) {
            System.err.println("Usage: average rating <in> [<in>...] <out>");
            System.exit(2);
        }
 
        Job job = Job.getInstance(conf, "average rating");
        job.setJarByClass(AveRating.class);
        
        job.setMapperClass(AveRating.TokenizerMapper.class);
        //job.setCombinerClass(AveRating.AverageFloatReducer.class);
        job.setReducerClass(AveRating.AverageFloatReducer.class);
        
        job.setOutputKeyClass(Text.class);
        job.setOutputValueClass(FloatWritable.class);
 
        for(int i = 0; i < otherArgs.length - 1; ++i) {
            FileInputFormat.addInputPath(job, new Path(otherArgs[i]));
        }
 
        FileOutputFormat.setOutputPath(job, new Path(otherArgs[otherArgs.length - 1]));
        System.exit(job.waitForCompletion(true)?0:1);
    }
 
    public static class TokenizerMapper extends Mapper<Object, Text, Text, FloatWritable> {
        private FloatWritable rats = new FloatWritable();
        private Text movie = new Text();
        public TokenizerMapper() {
        }
 
        public void map(Object key, Text value,Context context) throws IOException, InterruptedException {
//            StringTokenizer itr = new StringTokenizer(value.toString());
//            int head = 1;
        	String itr = new String(value.toString());
        	int rowid = Integer.parseInt(key.toString());
            	if(rowid != 0){
            		String[] row = itr.split(",");
            		//String userid = new String(row[0]);
            		String movieid = new String(row[1]);
            		float rating = Float.parseFloat(row[2]);
            		this.movie.set(movieid);
            		this.rats.set(rating);
            		context.write(this.movie,this.rats);
            	}
        }
    }
    
    public static class AverageFloatReducer extends Reducer<Text, FloatWritable, Text, FloatWritable> {
        private FloatWritable result = new FloatWritable();
 
        public AverageFloatReducer() {
        }
 
        public void reduce(Text key, Iterable<FloatWritable> values, Reducer<Text, FloatWritable, Text, FloatWritable>.Context context) throws IOException, InterruptedException {
            float sum = 0;
            int count = 0;
//          FloatWritable val;
//          for(Iterator i$ = values.iterator(); i$.hasNext(); sum += val.get()) {
//          	val = (FloatWritable)i$.next();
//          	count++;
//      	}
            for (FloatWritable i : values) {
				sum += i.get();		//求和
				count++;			//计数
			}
            float fcount = (float)count;
            sum = sum/fcount;		//计算平均分
            this.result.set(sum);
            String dotkey = new String(key.toString());
            dotkey = dotkey+',';	//给key加逗号是为了第二题合并的时候，统一和movie的格式，便于map切分字段
            key.set(dotkey);
            context.write(key, this.result);
        }
    }
    
    

}
