package com.exoreaction.quadim.dto.skill;

import com.exoreaction.quadim.dto.skill.SkillArea.SkillAreaClassification;
import com.exoreaction.quadim.dto.skill.SkillCategory.SkillCategoryClassification;
import com.exoreaction.quadim.dto.skill.SkillType.SkillTypeClassification;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class SimplifiedSkill {

  private String id;

  private String name;

  private String name_en;

  private String description;

  private String description_en;

  private SkillCategoryClassification coversElement = SkillCategoryClassification.NotSet; //  Relationship, Communication, Management_Leadership, Analytical, Creative, Physical, Technical, NotSet

  private SkillTypeClassification skillType = SkillTypeClassification.NotSet; //Transferable_and_Functional, Personal_Trait_or_Attitude, Knowledge_Based, NotSet

  private Set<SkillAreaClassification> matchesArea = new HashSet<SkillArea.SkillAreaClassification>(); //Technical, Organizational, Process, Experience, Domain, NotSet

  private Set<String> relatesTo = new HashSet<String>(); // for example: Java ref_id, Ruby ref_id, Python ref_id, PHP ref_id, etc.

  private Set<String> isCompositeOf = new HashSet<String>();

  private Set<String> isExtensionOf = new HashSet<String>();

  private Set<SkillTag> taggedWith = new HashSet<SkillTag>();

  //more extended properties
  private boolean isPublic;

  private String skillOwner;

  private String skillOwnerUsername;

  private String editedBy;

  private String editedByUsername;

  private String networkReference;

  private int currentVersion;

  private LocalDateTime createdAt;

  private LocalDateTime lastEdited;

  private String additionaljsonproperties;

  private boolean hide;


  public SimplifiedSkill(Skill skill) {
    this.setId(skill.getId());
    this.setName(skill.getName());
    this.setDescription(skill.getDescription());
    this.setDescription_en(skill.getDescription_en());
    this.setCoversElement(skill.getCoversElement().getClassification());
    this.setSkillType(skill.getSkillType().getSkillTypeClassification());
    this.setMatchesArea(skill.getMatchesArea().stream().map(e -> { return e.getSkillAreaClassification(); }).collect(
        Collectors.toSet()));
    this.setTaggedWith(new HashSet<SkillTag>(skill.getTaggedWith()));
    this.setIsCompositeOf(skill.getIsCompositeOf().stream().map(e -> {return e.getId();}).collect(Collectors.toSet()));
    this.setIsExtensionOf(skill.getIsExtensionOf().stream().map(e -> {return e.getId();}).collect(Collectors.toSet()));
    this.setRelatesTo(skill.getRelatesTo().stream().map(e -> {return e.getId();}).collect(Collectors.toSet()));
    this.setPublic(skill.isPublic());
    this.setSkillOwner(skill.getSkillOwner());
    this.setSkillOwnerUsername(skill.getSkillOwnerUsername());
    this.setEditedBy(skill.getEditedBy());
    this.setEditedByUsername(skill.getEditedByUsername());
    this.setNetworkReference(skill.getNetworkReference());
    this.setCurrentVersion(skill.getCurrentVersion());
    this.setCreatedAt(skill.getCreatedAt());
    this.setLastEdited(skill.getLastEdited());
    this.setName_en(skill.getName_en());
    this.setAdditionaljsonproperties(skill.getAdditionaljsonproperties());
  }


}