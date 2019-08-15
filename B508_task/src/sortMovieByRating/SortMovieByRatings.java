package sortMovieByRating;
/*
 * 对电影按照平均分进行排序
 * 输入：movie_id ratings
 * 输出：title ratings
 * 先统计电影的平均分，第一题已经完成 aveRatings
 * 按照分数对id进行降序排序
 * 对排序结果与movie进行联合
 * */

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.FloatWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.util.GenericOptionsParser;

public class SortMovieByRatings {
	public static void main(String[] args) throws Exception {

        Configuration conf = new Configuration();
        String[] otherArgs = (new GenericOptionsParser(conf, args)).getRemainingArgs();
        if(otherArgs.length < 2) {
            System.err.println("Usage: sort movie by averatinng <in> [movies,aveRatings] <out> [finalMovieSort] <tmp> [joinByMovieId]");
            System.exit(2);
        }
        
        Job jobJoin = Job.getInstance(conf, "join movie and rating by id.");
        jobJoin.setJarByClass(JoinTitleAndId.class);
        
        jobJoin.setMapperClass(JoinTitleAndId.JoinMapper.class);
        jobJoin.setReducerClass(JoinTitleAndId.ValueToKeyReducer.class);
        
        jobJoin.setMapOutputKeyClass(Text.class);
        jobJoin.setMapOutputValueClass(Text.class);
        
        jobJoin.setOutputKeyClass(Text.class);
        jobJoin.setOutputValueClass(FloatWritable.class);
        //定义input和output参数
        FileInputFormat.addInputPath(jobJoin, new Path(otherArgs[0]));
        FileInputFormat.addInputPath(jobJoin, new Path(otherArgs[1]));
        FileOutputFormat.setOutputPath(jobJoin, new Path(otherArgs[2]));
        int joinFinish= jobJoin.waitForCompletion(true)?0:1;
        //System.exit(jobJoin.waitForCompletion(true)?0:1); //exit(0)正常退出，exit(1)捕获异常退出。
        
        
        if(joinFinish == 0){
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
	        FileInputFormat.addInputPath(jobsort, new Path(otherArgs[2]));
	        FileOutputFormat.setOutputPath(jobsort, new Path(otherArgs[otherArgs.length - 1]));
	        int sortFinish = jobsort.waitForCompletion(true)?0:1;
	        System.exit(sortFinish);
	        //System.exit(jobsort.waitForCompletion(true)?0:1);
        }
        else {
			System.exit(1);
		}
    }
}
