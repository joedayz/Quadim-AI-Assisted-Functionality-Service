package com.exoreaction.quadim.dto.skill;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.*;


public class Skill implements Serializable, Cloneable {

  public static final ObjectMapper mapper = new ObjectMapper().configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
  private static final Logger log = LoggerFactory.getLogger(Skill.class);

  private String name;
  private String name_en;
  private String id;
  private String description;
  private String description_en;

  private SkillCategory coversElement = new SkillCategory(SkillCategory.SkillCategoryClassification.NotSet);
  private List<SkillTag> taggedWith = new LinkedList<>();
  private List<SkillArea> matchesArea = new LinkedList();
  private SkillType skillType = new SkillType(SkillType.SkillTypeClassification.NotSet);

  private Set<SkillReference> relatesTo = new HashSet<>();  // model weigth, significance
  private Set<SkillReference> isCompositeOf = new HashSet<>();  // model weigth, significance
  private Set<SkillReference> isExtensionOf = new HashSet<>();  // model weigth, significance

  private String additionaljsonproperties;

  private boolean isPublic;

  private String skillOwner;

  private String skillOwnerUsername;

  private String editedBy;

  private String editedByUsername;

  private String networkReference;

  private int currentVersion;

  private LocalDateTime createdAt;

  private LocalDateTime lastEdited;

  private boolean hide;

  public Skill() {
    id = UUID.randomUUID().toString();
  }

  public Skill(String name, String name_en) {
    id = UUID.randomUUID().toString();
    this.name = name;
    this.name_en = name_en;
  }



  public static ObjectMapper getObjectMapperForSparseObject() {
    String[] fieldsToSkip = new String[]{"getRelatesToSkill", "getIsExtensionOfSkill", "getIsCompositeOfSkill"};


    final SimpleFilterProvider filter = new SimpleFilterProvider();
    filter.addFilter("custom_serializer",
        SimpleBeanPropertyFilter.serializeAllExcept(fieldsToSkip));

    mapper.setFilters(filter);
    return mapper;
  }

  public static ObjectMapper getObjectMapperForFullObject() {
    String[] fieldsToSkip = new String[]{"password"};


    final SimpleFilterProvider filter = new SimpleFilterProvider();
    filter.addFilter("custom_serializer",
        SimpleBeanPropertyFilter.serializeAllExcept(fieldsToSkip));

    mapper.setFilters(filter);
    return mapper;
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

  public List<SkillReference> getRelatesTo() {

    return new ArrayList(relatesTo);
  }

  public List<Skill> getRelatesToSkill() {
    List<Skill> skillList = new LinkedList<>();
    for (SkillReference skillReference : relatesTo) {
      if (SkillFactory.hasSkillByName(skillReference.getName())) {
        if (SkillFactory.hasSkillByNameAndId(skillReference.getName(), skillReference.getId())) {
          skillList.add(SkillFactory.getSkillById(skillReference.getName(), skillReference.getId()));
        } else {
          skillList.add(SkillFactory.getSkillByName(skillReference.getName()));
        }
      }
      skillList.add(SkillFactory.getSkillByName(skillReference.getName()));
    }
    return skillList;
  }

  public void setRelatesTo(List<Skill> relatesTo) {
    for (Skill skill : relatesTo) {
      SkillReference skillReference = new SkillReference();
      skillReference.setId(skill.getId());
      skillReference.setName(skill.getName());
      this.relatesTo.add(skillReference);
    }
  }

  @JsonIgnore
  public String getRelatesToJson() {
    try {
      return mapper.writerWithDefaultPrettyPrinter().writeValueAsString(getRelatesTo());
    } catch (Exception e) {
      log.warn("Unable to serialize relatesToSet: {}", e);
    }
    return "{}";
  }

  public void setRelatesToJson(String full_json) {
    try {
      setRelatesTo(mapper.readValue(full_json, new TypeReference<List<Skill>>() {
      }));
      log.debug("Received relatesToList:{}", relatesTo);
      // set object from fulljson
    } catch (Exception e) {
      log.warn("Unable to unmarshall relatesToSet: {}", e);
    }
  }

  public List<SkillReference> getIsCompositeOf() {
    return new ArrayList(isCompositeOf);
  }

  public List<Skill> getIsCompositeOfSkill() {
    List<Skill> skillList = new LinkedList<>();
    for (SkillReference skillReference : isCompositeOf) {
      skillList.add(SkillFactory.getSkillById(skillReference.getName(), skillReference.getId()));
    }
    return skillList;
  }

  public void setIsCompositeOf(List<Skill> isCompositeOf) {
    for (Skill skill : isCompositeOf) {
      SkillReference skillReference = new SkillReference();
      skillReference.setId(skill.getId());
      skillReference.setName(skill.getName());
      this.isCompositeOf.add(skillReference);
    }
  }

  @JsonIgnore
  public String getIsCompositeOfJson() {
    try {
      return mapper.writerWithDefaultPrettyPrinter().writeValueAsString(getIsCompositeOf());
    } catch (Exception e) {
      log.warn("Unable to serialize isCompositeOfSet: {}", e);
    }
    return "{}";
  }

  public void setIsCompositeOfJson(String full_json) {
    try {
      setIsCompositeOf(mapper.readValue(full_json, new TypeReference<List<Skill>>() {
      }));
      log.debug("Received  isCompositeOfList:{}", relatesTo);
      // set object from fulljson
    } catch (Exception e) {
      log.warn("Unable to unmarshall isCompositeOfSet: {}", e);
    }
  }


  public List<SkillReference> getIsExtensionOf() {
    return new ArrayList(isExtensionOf);
  }

  public List<Skill> getIsExtensionOfSkill() {
    List<Skill> skillList = new LinkedList<>();
    for (SkillReference skillReference : isExtensionOf) {
      if (SkillFactory.hasSkillByNameAndId(skillReference.getName(), skillReference.getId())) {
        skillList.add(SkillFactory.getSkillById(skillReference.getName(), skillReference.getId()));
      } else {
        skillList.add(SkillFactory.getSkillByName(skillReference.getName()));
      }
      skillList.add(SkillFactory.getSkillByName(skillReference.getName()));
    }
    return skillList;
  }

  public void setIsExtensionOf(List<Skill> isExtensionOf) {
    for (Skill skill : isExtensionOf) {
      SkillReference skillReference = new SkillReference();
      skillReference.setId(skill.getId());
      skillReference.setName(skill.getName());
      this.isExtensionOf.add(skillReference);
    }
  }

  @JsonIgnore
  public String getIsExtensionOfJson() {
    try {
      return mapper.writerWithDefaultPrettyPrinter().writeValueAsString(getIsExtensionOf());
    } catch (Exception e) {
      log.warn("Unable to serialize IsExtensionOfSet: {}", e);
    }
    return "{}";
  }

  public void setIsExtensionOfJson(String full_json) {
    try {
      setIsExtensionOf(mapper.readValue(full_json, new TypeReference<List<Skill>>() {
      }));
      log.debug("Received  IsExtensionOfList:{}", relatesTo);
      // set object from fulljson
    } catch (Exception e) {
      log.warn("Unable to unmarshall IsExtensionOfSet: {}", e);
    }
  }


  public String getDescription() {
    if (description == null || description.length() < 1) {
      return getName();
    }
    return description;
  }

  public void setDescription(String description) {

    this.description = description;
    if (description != null && description.length() > 5) {
      this.description = this.description.replace("TAG_TO_BE_DEFINED", "");
    }

  }

  public String getDescription_en() {
    if (description_en == null || description_en.length() < 1) {
      return getDescription();
    }
    return description_en;
  }

  public void setDescription_en(String description_en) {

    this.description_en = description_en;
    if (description_en != null && description_en.length() > 5) {
      this.description_en = this.description_en.replace("TAG_TO_BE_DEFINED", "");
    }
  }


  @Override
  public String toString() {
    return "Skill{" +
        "name='" + name + '\'' +
        ", id='" + id + '\'' +
        ", description='" + description + '\'' +
        ", description_en='" + description_en + '\'' +
        ", coversElement=" + coversElement +
        ", taggedWith=" + taggedWith +
        ", matchesArea=" + matchesArea +
        ", skillType=" + skillType +
        ", relatesTo=" + relatesTo +
        ", isCompositeOf=" + isCompositeOf +
        ", isExtensionOf=" + isExtensionOf +
        ", isPublic=" + isPublic +
        ", skillOwner=" + skillOwner +
        ", skillOwnerUsername=" + skillOwnerUsername +
        ", editedBy=" + editedBy +
        ", editedByUsername=" + editedByUsername +
        ", networkReference=" + networkReference +
        ", currentVersion=" + currentVersion +
        ", createdAt=" + createdAt.toString() +
        ", lastEdited=" + lastEdited.toString() +
        '}';
  }


  public boolean isPublic() {
    return isPublic;
  }


  public void setPublic(boolean isPublic) {
    this.isPublic = isPublic;
  }


  public String getSkillOwner() {
    return skillOwner;
  }


  public void setSkillOwner(String skillOwner) {
    this.skillOwner = skillOwner;
  }


  public String getEditedBy() {
    return editedBy;
  }


  public void setEditedBy(String editedBy) {
    this.editedBy = editedBy;
  }


  public String getNetworkReference() {
    return networkReference;
  }


  public void setNetworkReference(String networkReference) {
    this.networkReference = networkReference;
  }


  public int getCurrentVersion() {
    return currentVersion;
  }


  public void setCurrentVersion(int currentVersion) {
    this.currentVersion = currentVersion;
  }


  public LocalDateTime getCreatedAt() {
    return createdAt;
  }


  public void setCreatedAt(LocalDateTime createdAt) {
    this.createdAt = createdAt;
  }


  public LocalDateTime getLastEdited() {
    return lastEdited;
  }


  public void setLastEdited(LocalDateTime lastEdited) {
    this.lastEdited = lastEdited;
  }


  public String getSkillOwnerUsername() {
    return skillOwnerUsername;
  }


  public void setSkillOwnerUsername(String skillOwnerUsername) {
    this.skillOwnerUsername = skillOwnerUsername;
  }


  public String getEditedByUsername() {
    return editedByUsername;
  }


  public void setEditedByUsername(String editedByUsername) {
    this.editedByUsername = editedByUsername;
  }


  public String getName_en() {
    return name_en;
  }


  public void setName_en(String name_en) {
    this.name_en = name_en;
  }


  public String getAdditionaljsonproperties() {
    return additionaljsonproperties;
  }


  public void setAdditionaljsonproperties(String additionaljsonproperties) {
    this.additionaljsonproperties = additionaljsonproperties;
  }

  public boolean isHide() {
    return hide;
  }

  public void setHide(boolean hide) {
    this.hide = hide;
  }
}