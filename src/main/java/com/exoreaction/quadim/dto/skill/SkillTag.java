package com.exoreaction.quadim.dto.skill;

import java.io.Serializable;

public class SkillTag implements Serializable, Cloneable {

  public SkillTag() {
  }

  public SkillTag(String tagName) {
    this.tagName = tagName;
    this.tagValue = "";
  }

  public SkillTag(String tagName, String tagValue) {
    this.tagName = tagName;
    this.tagValue = tagValue;
  }

  private String tagName = "";
  private String tagValue = "";

  public String getTagName() {
    return tagName;
  }

  public void setTagName(String tagName) {
    this.tagName = tagName;
  }

  public String getTagValue() {
    return tagValue;
  }

  public void setTagValue(String tagValue) {
    this.tagValue = tagValue;
  }
}
