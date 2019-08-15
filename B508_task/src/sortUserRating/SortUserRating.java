package sortUserRating;

import java.io.IOException;
import java.util.Iterator;

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

/*
 * 对用户按照评分的次序进行排序
 * 先对用户的评分次数进行累加，userRateTimes
 * 对评分次数表进行降序排序 SortedRateTimes
 * */
public class SortUserRating {

    public SortUserRating() {
    }
 
    public static void main(String[] args) throws Exception {
        Configuration conf = new Configuration();
        String[] otherArgs = (new GenericOptionsParser(conf, args)).getRemainingArgs();
        if(otherArgs.length < 2) {
            System.err.println("Usage: sort user ratinng <in> [<in>...] <out>");
            System.exit(2);
        }
 
        Job job = Job.getInstance(conf, "Add user rating");
        job.setJarByClass(SortUserRating.class);
        
        job.setMapperClass(SortUserRating.TokenizerMapper.class);
        job.setCombinerClass(SortUserRating.TimeSumReducer.class);
        job.setReducerClass(SortUserRating.TimeSumReducer.class);
        
        job.setOutputKeyClass(Text.class);
        job.setOutputValueClass(IntWritable.class);
 
        FileInputFormat.addInputPath(job, new Path(otherArgs[0]));
        FileOutputFormat.setOutputPath(job, new Path(otherArgs[1]));
        
        
        
        
        
        Job jobsort = Job.getInstance(conf, "Sort user rating");
        jobsort.setJarByClass(SortByValue.class);
        
        jobsort.setMapperClass(SortByValue.ReverseMapper.class);
        //jobsort.setCombinerClass(SortByValue.SortValueReducer.class);
        jobsort.setReducerClass(SortByValue.SortValueReducer.class);
        
        jobsort.setMapOutputKeyClass(IntWritable.class);
        jobsort.setMapOutputValueClass(Text.class);
        jobsort.setOutputKeyClass(Text.class);
        jobsort.setOutputValueClass(IntWritable.class);
        
		jobsort.setSortComparatorClass(SortByValue.DescSort.class);//设置Sort阶段使用比较器
        
        //定义input和output参数
        FileInputFormat.addInputPath(jobsort, new Path(otherArgs[1]));
        FileOutputFormat.setOutputPath(jobsort, new Path(otherArgs[otherArgs.length - 1]));
        
        System.exit(job.waitForCompletion(true)&&jobsort.waitForCompletion(true)?0:1);
    }
    
    public static class TokenizerMapper extends Mapper<Object, Text, Text, IntWritable> {
        private static final IntWritable one = new IntWritable(1);
        private Text user = new Text();
 
        public TokenizerMapper() {
        }
        public void map(Object key, Text value, Mapper<Object, Text, Text, IntWritable>.Context context) throws IOException, InterruptedException {
        	String itr = new String(value.toString());
        	int rowid = Integer.parseInt(key.toString());
            if(rowid != 0){
        		String[] row = itr.split(",");
        		String userid = new String(row[0]);
//        		String movieid = new String(row[1]);
//        		float rating = Float.parseFloat(row[2]);
        		this.user.set(userid);
        		context.write(this.user,one);	//每出现一次，计数一次
        	} 
        }
    }
 
    public static class TimeSumReducer extends Reducer<Text, IntWritable, Text, IntWritable> {
        private IntWritable result = new IntWritable();
 
        public TimeSumReducer() {}
 
        public void reduce(Text key, Iterable<IntWritable> values, Reducer<Text, IntWritable, Text, IntWritable>.Context context) throws IOException, InterruptedException {
            int sum = 0;
            IntWritable val;
            for(Iterator i$ = values.iterator(); i$.hasNext(); sum += val.get()) {
                val = (IntWritable)i$.next();	//累加所有次数
            }
            this.result.set(sum);
            context.write(key, this.result);
        }
    }

}
