package se.raihle.tollcalculator.schedule;

import java.time.LocalTime;
import java.util.LinkedList;
import java.util.List;

public class ScheduleBuilder {
	public static ScheduleBuilder start(int feeFromMidnight) {
		return new ScheduleBuilder(feeFromMidnight);
	}

	private final List<Part> parts;
	private int currentFee;
	private LocalTime currentStart;
	private boolean finished;

	private ScheduleBuilder(int feeFromMidnight) {
		parts = new LinkedList<>();
		currentFee = feeFromMidnight;
		currentStart = LocalTime.MIN;
		finished = false;
	}

	public ScheduleBuilder next(LocalTime time, int fee) {
		assertNotFinished();
		endCurrentPartAt(time);
		startNewPart(time, fee);
		return this;
	}

	public Schedule finish() {
		assertNotFinished();
		endCurrentPartAt(LocalTime.MAX);
		finished = true;
		return new Schedule(parts);
	}

	private void endCurrentPartAt(LocalTime untilExclusive) {
		parts.add(new Part(currentStart, untilExclusive, currentFee));
	}

	private void startNewPart(LocalTime fromInclusive, int fee) {
		currentStart = fromInclusive;
		currentFee = fee;
	}

	private void assertNotFinished() {
		if (finished) {
			throw new IllegalStateException("Tried to add a new part to a finished ScheduleBuilder");
		}
	}
}
