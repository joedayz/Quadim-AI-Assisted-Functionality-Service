package com.exoreaction.quadim.dto.skill;

import java.io.Serializable;

public class SkillType implements Serializable, Cloneable {

  public SkillType() {
    this.skillTypeClassification = SkillTypeClassification.NotSet;
  }

  public SkillType(SkillTypeClassification skillTypeClassification) {
    this.skillTypeClassification = skillTypeClassification;
  }

  private SkillTypeClassification skillTypeClassification;

  public SkillTypeClassification getSkillTypeClassification() {
    return skillTypeClassification;
  }

  public void setSkillTypeClassification(SkillTypeClassification skillTypeClassification) {
    this.skillTypeClassification = skillTypeClassification;
  }

  @Override
  public String toString() {
    return "SkillType{" +
        "skillTypeClassification=" + skillTypeClassification +
        '}';
  }

  public enum SkillTypeClassification {
    Transferable_and_Functional, Personal_Trait_or_Attitude, Knowledge_Based, NotSet
  }
}
