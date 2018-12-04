package com.viator.connector.domain.viator.pricingmatrix;

import java.util.List;

public class ViatorPricingMatrixResTourGrade {

	private String sortOrder;

	private String gradeCode;

	private String gradeTitle;

	private List<ViatorPricingMatrixResPricingMatrix> viatorPricingMatrixResPricingMatrix;

	public String getSortOrder() {
		return sortOrder;
	}

	public void setSortOrder(String sortOrder) {
		this.sortOrder = sortOrder;
	}

	public String getGradeCode() {
		return gradeCode;
	}

	public void setGradeCode(String gradeCode) {
		this.gradeCode = gradeCode;
	}

	public String getGradeTitle() {
		return gradeTitle;
	}

	public void setGradeTitle(String gradeTitle) {
		this.gradeTitle = gradeTitle;
	}

	public List<ViatorPricingMatrixResPricingMatrix> getPricingMatrix() {
		return viatorPricingMatrixResPricingMatrix;
	}

	public void setPricingMatrix(List<ViatorPricingMatrixResPricingMatrix> viatorPricingMatrixResPricingMatrix) {
		this.viatorPricingMatrixResPricingMatrix = viatorPricingMatrixResPricingMatrix;
	}
}
