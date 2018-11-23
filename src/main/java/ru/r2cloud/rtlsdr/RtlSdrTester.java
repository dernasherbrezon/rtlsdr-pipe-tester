package ru.r2cloud.rtlsdr;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import com.beust.jcommander.JCommander;
import com.codahale.metrics.Counter;
import com.codahale.metrics.SharedMetricRegistries;

public class RtlSdrTester {

	public static void main(String[] args) throws Exception {
		CopyConfig config = new CopyConfig();
		JCommander jCommander = new JCommander(config, args);
		if (config.isHelp()) {
			jCommander.usage();
			return;
		}
		
		final CopyProcess cp = new CopyProcess();
		final Counter bytes = SharedMetricRegistries.getOrCreate("rtlsdrtester").counter("bytes");
		final ScheduledExecutorService executor = Executors.newScheduledThreadPool(1);
		executor.scheduleAtFixedRate(new Runnable() {

			private long previousValue = 0;

			public void run() {
				long current = bytes.getCount();
				long diff = current - previousValue;
				previousValue = current;
				System.out.println(diff);
			}
		}, 1000, 1000, TimeUnit.MILLISECONDS);

		Runnable shutdown = new Runnable() {

			public void run() {
				try {
					cp.stop();
				} catch (Exception e) {
					e.printStackTrace();
				}
				executor.shutdown();
			}
		};
		
		Runtime.getRuntime().addShutdownHook(new Thread(shutdown));

		cp.copy(config);
		shutdown.run();
	}
	

}
