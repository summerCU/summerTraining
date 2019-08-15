package sortMovieByRating;

import java.io.IOException;


import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.FloatWritable;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.io.WritableComparator;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.util.GenericOptionsParser;


public class SortByRatings {
	public SortByRatings(){}
	//重写排序函数，实现降序排序
	public static class FloatWritableDecreasingComparator extends FloatWritable.Comparator {
		public int compare(byte[] b1, int s1, int l1, byte[] b2, int s2, int l2) {
			return -super.compare(b1, s1, l1, b2, s2, l2);
		}
	}

    public static class ReverseMapper extends Mapper<Object, Text, FloatWritable, Text> {
        private Text movie = new Text();
        private FloatWritable aveRatings = new FloatWritable();
        public ReverseMapper() {}
        //map过程，实现k，v翻转
        public void map(Object key, Text value,Context context) throws IOException, InterruptedException {
        	String itr = new String(value.toString());
        	if(!(itr.isEmpty())){
        		//String[] row = itr.split("\\s+");
        		String[] row = itr.split(",(?=([^\"]*\"[^\"]*\")*[^\"]*$)");
        		String userid = new String(row[0]);
        		float times = Float.parseFloat(row[1]);
        		this.movie.set(userid);
        		this.aveRatings.set(times);
        		context.write(this.aveRatings,this.movie); 
        	}
        }
    }
    //reduce实现第二次k,v翻转
    public static class SortValueReducer extends Reducer<FloatWritable, Text, Text, FloatWritable> {
        public SortValueReducer() {}
        @Override
        public void reduce(FloatWritable key, Iterable<Text> values,Context context) throws IOException, InterruptedException {
        	 for (Text i : values){
        		 context.write(i, key);
        	 }
        	
        }
    }
	/*
    public static void main(String[] args) throws Exception {
        Configuration conf = new Configuration();
        String[] otherArgs = (new GenericOptionsParser(conf, args)).getRemainingArgs();
        if(otherArgs.length < 2) {
            System.err.println("Usage: sort user ratinng <in> [<in>...] <out>");
            System.exit(2);
        }
        
        Job jobsort = Job.getInstance(conf, "Sort movie rating");
        jobsort.setJarByClass(SortByRatings.class);
        
        jobsort.setMapperClass(SortByRatings.ReverseMapper.class);
        jobsort.setReducerClass(SortByRatings.SortValueReducer.class);
        jobsort.setSortComparatorClass(SortByRatings.FloatWritableDecreasingComparator.class);
        
        jobsort.setMapOutputKeyClass(FloatWritable.class);
        jobsort.setMapOutputValueClass(Text.class);
        
        jobsort.setOutputKeyClass(Text.class);
        jobsort.setOutputValueClass(FloatWritable.class);
        //定义input和output参数
        FileInputFormat.addInputPath(jobsort, new Path(otherArgs[0]));
        FileOutputFormat.setOutputPath(jobsort, new Path(otherArgs[otherArgs.length - 1]));
        
        System.exit(jobsort.waitForCompletion(true)?0:1);
    }
*/
}
