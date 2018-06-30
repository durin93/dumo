package com.durin.dto;

import java.util.List;

import com.durin.domain.Label;


public class LabelsDto {
	
	private List<Label> labels ;

	public LabelsDto() {
	}

	public LabelsDto(List<Label> labels) {
		this.labels = labels;
	}

	public List<Label> getLabels() {
		return labels;
	}

	public void setLabels(List<Label> labels) {
		this.labels = labels;
	}
	
	public static LabelsDto of(List<Label> labels) {
		return new LabelsDto(labels);
	}
	
	
}
