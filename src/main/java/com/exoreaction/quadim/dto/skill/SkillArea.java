package com.exoreaction.quadim.dto.skill;

import java.io.Serializable;

public class SkillArea implements Serializable, Cloneable {

  public SkillArea() {
    this.skillAreaClassification = SkillAreaClassification.NotSet;
  }

  public SkillArea(SkillAreaClassification skillAreaClassification) {
    this.skillAreaClassification = skillAreaClassification;
  }

  private SkillAreaClassification skillAreaClassification;

  public SkillAreaClassification getSkillAreaClassification() {
    return skillAreaClassification;
  }

  public void setSkillAreaClassification(SkillAreaClassification skillAreaClassification) {
    this.skillAreaClassification = skillAreaClassification;
  }

  @Override
  public String toString() {
    return "SkillArea{" +
        "skillAreaClassification=" + skillAreaClassification +
        '}';
  }

  public enum SkillAreaClassification {
    Technical, Organizational, Process, Experience, Domain, NotSet
  }
}

