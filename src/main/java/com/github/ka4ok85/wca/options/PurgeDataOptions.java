package com.github.ka4ok85.wca.options;

public class PurgeDataOptions extends AbstractOptions {
	private final Long targetId;
	private final Long sourceId;

	public PurgeDataOptions(Long targetId, Long sourceId) {
		super();
		if (targetId < 1) {
			throw new RuntimeException("Target ID must be greater than zero. Provided Target ID = " + targetId);
		}

		if (sourceId < 1) {
			throw new RuntimeException("Source ID must be greater than zero. Provided Source ID = " + sourceId);
		}

		this.targetId = targetId;
		this.sourceId = sourceId;
	}

	public Long getTargetId() {
		return targetId;
	}

	public Long getSourceId() {
		return sourceId;
	}

	@Override
	public String toString() {
		return "PurgeDataOptions [targetId=" + targetId + ", sourceId=" + sourceId + "]";
	}

}
