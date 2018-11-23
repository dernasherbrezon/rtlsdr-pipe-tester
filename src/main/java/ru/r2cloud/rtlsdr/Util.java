package ru.r2cloud.rtlsdr;

import java.util.concurrent.TimeUnit;

public final class Util {

	public static void shutdown(Process process, long timeoutMillis) {
		if (process == null || !process.isAlive()) {
			return;
		}
		try {
			process.destroy();
			if (!process.waitFor(timeoutMillis, TimeUnit.MILLISECONDS)) {
				process.destroyForcibly().waitFor();
			}

		} catch (InterruptedException e) {
			Thread.currentThread().interrupt();
		}
	}

	private Util() {
		// do nothing
	}

}
