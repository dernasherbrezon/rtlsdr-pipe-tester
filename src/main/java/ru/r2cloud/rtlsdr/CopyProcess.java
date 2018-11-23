package ru.r2cloud.rtlsdr;

import java.io.File;
import java.io.IOException;
import java.lang.ProcessBuilder.Redirect;
import java.util.regex.Pattern;

import com.codahale.metrics.Counter;
import com.codahale.metrics.SharedMetricRegistries;

public class CopyProcess {

	private final static Pattern SPACE = Pattern.compile("\\s");
	private Process rtlSdr;
	private Process sox;
	private Counter counter;

	public CopyProcess() {
		counter = SharedMetricRegistries.getOrCreate("rtlsdrtester").counter("bytes");
	}

	public void copy(CopyConfig config) {

		try {
			sox = new ProcessBuilder(SPACE.split((config.getSoxPath() + " --type raw --rate " + config.getInputSampleRate() + " --encoding unsigned-integer --bits 8 --channels 2 - " + new File(config.getFile()).getAbsolutePath() + " rate " + config.getOutputSampleRate()))).redirectError(Redirect.INHERIT).start();
			rtlSdr = new ProcessBuilder(SPACE.split((config.getRtlSdrPath() + " -f " + String.valueOf(config.getFrequency()) + " -s " + config.getInputSampleRate() + " -g 45 -p 0 - "))).redirectError(Redirect.INHERIT).start();
			byte[] buf = new byte[config.getBufSize()];
			while (!Thread.currentThread().isInterrupted()) {
				int r = rtlSdr.getInputStream().read(buf);
				if (r == -1) {
					break;
				}
				sox.getOutputStream().write(buf, 0, r);
				counter.inc(buf.length);
			}
			sox.getOutputStream().flush();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			Util.shutdown(rtlSdr, 10000);
			Util.shutdown(sox, 10000);
		}
	}

	public void stop() {
		Util.shutdown(rtlSdr, 10000);
		rtlSdr = null;
	}

}
