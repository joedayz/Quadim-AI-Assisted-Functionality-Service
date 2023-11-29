package com.exoreaction.quadim.dto.skill;

import com.fasterxml.jackson.annotation.JsonIgnore;
import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

public class SkillReference implements Serializable, Cloneable {
  private String id;
  private String name;
  private String description;
  private String description_en;

  @JsonIgnore
  private List<String> list_of_related_skill_refs = new LinkedList<>();
  @JsonIgnore
  private List<String> list_of_is_composite_of_skill_refs = new LinkedList<>();
  @JsonIgnore
  private List<String> list_of_is_extension_of_skill_refs = new LinkedList<>();
  @JsonIgnore
  private SkillCategory coversElement = new SkillCategory(SkillCategory.SkillCategoryClassification.NotSet);
  @JsonIgnore
  private List<SkillTag> taggedWith = new LinkedList<>();
  @JsonIgnore
  private List<SkillArea> matchesArea = new LinkedList();
  @JsonIgnore
  private SkillType skillType = new SkillType(SkillType.SkillTypeClassification.NotSet);

  @JsonIgnore
  private int weigth = 100;
  @JsonIgnore
  private int relevance = 100;

  public SkillReference() {
  }

  public SkillReference(Skill skill) {
    setId(skill.getId());
    setName(skill.getName());
    setDescription(skill.getDescription());
    setDescription_en(skill.getDescription_en());
    for (SkillReference skillReference : skill.getRelatesTo()) {
      list_of_related_skill_refs.add(skillReference.getId());
    }
    for (SkillReference skillReference : skill.getIsCompositeOf()) {
      list_of_is_composite_of_skill_refs.add(skillReference.getId());
    }
    for (SkillReference skillReference : skill.getIsExtensionOf()) {
      list_of_is_extension_of_skill_refs.add(skillReference.getId());
    }
    coversElement = skill.getCoversElement();
    taggedWith = skill.getTaggedWith();
    skillType = skill.getSkillType();
    matchesArea = skill.getMatchesArea();
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public int getWeigth() {
    return weigth;
  }

  public void setWeigth(int weigth) {
    this.weigth = weigth;
  }

  public int getRelevance() {
    return relevance;
  }

  public void setRelevance(int relevance) {
    this.relevance = relevance;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public String getDescription_en() {
    return description_en;
  }

  public void setDescription_en(String description_en) {
    this.description_en = description_en;
  }

  public List<String> getList_of_related_skill_refs() {
    return list_of_related_skill_refs;
  }

  public void setList_of_related_skill_refs(List<String> list_of_related_skill_refs) {
    this.list_of_related_skill_refs = list_of_related_skill_refs;
  }

  public List<String> getList_of_is_composite_of_skill_refs() {
    return list_of_is_composite_of_skill_refs;
  }

  public void setList_of_is_composite_of_skill_refs(List<String> list_of_is_composite_of_skill_refs) {
    this.list_of_is_composite_of_skill_refs = list_of_is_composite_of_skill_refs;
  }

  public List<String> getList_of_is_extension_of_skill_refs() {
    return list_of_is_extension_of_skill_refs;
  }

  public void setList_of_is_extension_of_skill_refs(List<String> list_of_is_extension_of_skill_refs) {
    this.list_of_is_extension_of_skill_refs = list_of_is_extension_of_skill_refs;
  }

  public SkillCategory getCoversElement() {
    return coversElement;
  }

  public void setCoversElement(SkillCategory coversElement) {
    this.coversElement = coversElement;
  }

  public List<SkillTag> getTaggedWith() {
    return taggedWith;
  }

  public void setTaggedWith(List<SkillTag> taggedWith) {
    this.taggedWith = taggedWith;
  }

  public List<SkillArea> getMatchesArea() {
    return matchesArea;
  }

  public void setMatchesArea(List<SkillArea> matchesArea) {
    this.matchesArea = matchesArea;
  }

  public SkillType getSkillType() {
    return skillType;
  }

  public void setSkillType(SkillType skillType) {
    this.skillType = skillType;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    SkillReference that = (SkillReference) o;
    return Objects.equals(id, that.id) &&
        Objects.equals(name, that.name);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, name);
  }

  @Override
  public String toString() {
    return "SkillReference{" +
        "name='" + name + '\'' +
        ", id='" + id + '\'' +
        ", description='" + description + '\'' +
        ", description_en='" + description_en + '\'' +
        ", list_of_related_skill_name=" + list_of_related_skill_refs +
        ", list_of_is_composite_of_skill_name_skills=" + list_of_is_composite_of_skill_refs +
        ", list_of_is_extension_of_skill_name_skills=" + list_of_is_extension_of_skill_refs +
        ", weigth=" + weigth +
        ", relevance=" + relevance +
        '}';
  }
}