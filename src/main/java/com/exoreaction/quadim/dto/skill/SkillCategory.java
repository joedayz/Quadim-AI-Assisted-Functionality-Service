package com.exoreaction.quadim.dto.skill;

import java.io.Serializable;

public class SkillCategory implements Serializable, Cloneable {
  public SkillCategory() {
    this.classification = SkillCategoryClassification.NotSet;
  }

  public SkillCategory(SkillCategoryClassification classification) {
    this.classification = classification;
  }

  private SkillCategoryClassification classification;

  public SkillCategoryClassification getClassification() {
    return classification;
  }

  public void setClassification(SkillCategoryClassification classification) {
    this.classification = classification;
  }

  @Override
  public String toString() {
    return "SkillCategory{" +
        "classification=" + classification +
        '}';
  }

  public enum SkillCategoryClassification {
    Relationship, Communication, Management_Leadership, Analytical, Creative, Physical, Technical, NotSet;
  }
}
