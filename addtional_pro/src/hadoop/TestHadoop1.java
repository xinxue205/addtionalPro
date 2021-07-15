package hadoop;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.input.TextInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.mapreduce.lib.output.TextOutputFormat;

import java.io.IOException;

class SortMapper extends Mapper<Object, Text, LongWritable, IntWritable> {
    private LongWritable num = new LongWritable(0);
    private IntWritable one = new IntWritable(1);
    @Override
    protected void map(Object key, Text value, Context context) throws IOException, InterruptedException {
        num.set(Long.valueOf(value.toString().trim()));
        context.write(num, one);
    }
}

class SortReducer extends Reducer<LongWritable, IntWritable, LongWritable, NullWritable> {
    @Override
    protected void reduce(LongWritable key, Iterable<IntWritable> values, Context context) throws IOException, InterruptedException {
        int count = 0;
        for (IntWritable i : values) {
            count += i.get();
        }
        for (int i=0; i<count; i++) {
            context.write(key, NullWritable.get());
        }
    }
}

public class TestHadoop1 {
    public static void main(String[] args) throws Exception {
        if (args.length != 2) {
            System.err.println("sort <input file/dir> <output dir>");
            return;
        }

        Configuration conf = new Configuration();

//        conf.set();

        Job job = Job.getInstance(conf, "sort-int");

        job.setJarByClass(TestHadoop1.class);

        job.setMapperClass(SortMapper.class);

        job.setReducerClass(SortReducer.class);

        job.setMapOutputKeyClass(LongWritable.class);
        job.setMapOutputValueClass(IntWritable.class);
        job.setOutputKeyClass(LongWritable.class);
        job.setOutputValueClass(NullWritable.class);

        job.setInputFormatClass(TextInputFormat.class);
        job.setOutputFormatClass(TextOutputFormat.class);

        FileInputFormat.addInputPath(job, new Path(args[0]));
        FileOutputFormat.setOutputPath(job, new Path(args[1]));

        job.setNumReduceTasks(1);
        job.waitForCompletion(true);
    }
}
