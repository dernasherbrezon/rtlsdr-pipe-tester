package ru.r2cloud.rtlsdr;

import com.beust.jcommander.Parameter;

public class CopyConfig {

	@Parameter(names = "--help")
	private boolean help = false;
	@Parameter(names = "--bufSize")
	private int bufSize = 0x1000;
	@Parameter(names = "--rtlsdr")
	private String rtlSdrPath = "rtl_sdr";
	@Parameter(names = "--sox")
	private String soxPath = "sox";
	@Parameter(names = "--input")
	private int inputSampleRate = 1440000;
	@Parameter(names = "--output")
	private int outputSampleRate = 150000;
	@Parameter(names = "--frequency")
	private long frequency = 137900000;
	@Parameter(names = "--file")
	private String file = "/tmp/test.wav";

	public String getFile() {
		return file;
	}

	public void setFile(String file) {
		this.file = file;
	}

	public long getFrequency() {
		return frequency;
	}

	public void setFrequency(long frequency) {
		this.frequency = frequency;
	}

	public int getInputSampleRate() {
		return inputSampleRate;
	}

	public void setInputSampleRate(int inputSampleRate) {
		this.inputSampleRate = inputSampleRate;
	}

	public int getOutputSampleRate() {
		return outputSampleRate;
	}

	public void setOutputSampleRate(int outputSampleRate) {
		this.outputSampleRate = outputSampleRate;
	}

	public String getRtlSdrPath() {
		return rtlSdrPath;
	}

	public void setRtlSdrPath(String rtlSdrPath) {
		this.rtlSdrPath = rtlSdrPath;
	}

	public String getSoxPath() {
		return soxPath;
	}

	public void setSoxPath(String soxPath) {
		this.soxPath = soxPath;
	}

	public int getBufSize() {
		return bufSize;
	}

	public void setBufSize(int bufSize) {
		this.bufSize = bufSize;
	}

	public boolean isHelp() {
		return help;
	}

	public void setHelp(boolean help) {
		this.help = help;
	}

}
