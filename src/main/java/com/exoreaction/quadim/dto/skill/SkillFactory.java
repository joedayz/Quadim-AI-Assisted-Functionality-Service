package com.exoreaction.quadim.dto.skill;

import java.io.PrintWriter;
import java.io.Serializable;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

public class SkillFactory implements Serializable, Cloneable {

  public static final Logger log = LoggerFactory.getLogger(SkillFactory.class);

  private static Map<String, Map<String, Skill>> registeredSkillList = new HashMap<>();
  private static final ObjectMapper mapper = Skill.getObjectMapperForSparseObject();

  public static String SKILL_DB_FILENAME = "./quadim_skill.json";

  public static final void clearCache() {
    registeredSkillList = new HashMap<>();
  }

  public static final boolean hasSkillByName(String skillname) {
    if (registeredSkillList.get(skillname) == null) {
      return true;
    }
    return false;
  }

  public static final boolean hasSkillByNameAndId(String skillname, String skillId) {
    if (registeredSkillList.get(skillname) != null) {
      Map<String, Skill> skillMap = registeredSkillList.get(skillname);
      for (String foundskillId : skillMap.keySet()) {
        if (foundskillId.equalsIgnoreCase(skillId)) {
          return true;

        }
      }
    }
    return false;
  }

  public static final Skill addNewSkill(Skill skill) {
    if (registeredSkillList.get(skill.getName()) == null) {
      Map<String, Skill> skillMap = new HashMap<>();
      skillMap.put(skill.getId(), skill);
      registeredSkillList.put(skill.getName(), skillMap);
      storeSkills();
    }
    return skill;

  }

  public static final Skill mergeSkill(Skill skillToMerge) {
    Map<String, Skill> skillMap = registeredSkillList.get(skillToMerge.getName());
    if (skillMap == null) {
      skillMap = new HashMap<>();
    }
    skillMap.put(getSkillByName(skillToMerge.getName()).getId(), skillToMerge);
    registeredSkillList.put(skillToMerge.getName(), skillMap);
    storeSkills();
    return skillToMerge;
  }

  public static final Skill getSkillByName(String skillname) {
    if (registeredSkillList.get(skillname) == null) {
      Skill skill = new Skill();
      skill.setName(skillname);
      Map<String, Skill> skillMap = new HashMap<>();
      skillMap.put(skill.getId(), skill);
      registeredSkillList.put(skillname, skillMap);

      storeSkills();
    }
    Map<String, Skill> matchingSkillMap = registeredSkillList.get(skillname);
    return matchingSkillMap.values().iterator().next();

  }

  public static final Skill getSkillById(String skillname, String skillId) {
    if (registeredSkillList.get(skillname) == null) {
      Skill skill = new Skill();
      skill.setName(skillname);
      Map<String, Skill> skillMap = new HashMap<>();
      skillMap.put(skill.getId(), skill);
      registeredSkillList.put(skillname, skillMap);

      storeSkills();
    }
    Map<String, Skill> matchingSkillMap = registeredSkillList.get(skillname);
    return matchingSkillMap.get(skillId);

  }

  public static final Skill updateSkillById(String skillname, String skillId, Skill skill) {
    if (registeredSkillList.get(skillname) == null) {
      Map<String, Skill> skillMap = new HashMap<>();
      skillMap.put(skill.getId(), skill);
      registeredSkillList.put(skillname, skillMap);
      storeSkills();
    }
    Map<String, Skill> matchingSkillMap = registeredSkillList.get(skillname);
    for (String matchskillid : matchingSkillMap.keySet()) {
      if (skillId.equalsIgnoreCase(matchskillid)) {
        matchingSkillMap.put(matchskillid, skill);
      }


    }
    return matchingSkillMap.get(skillId);

  }

  public static void storeSkills() {
    try (PrintWriter out = new PrintWriter(SKILL_DB_FILENAME)) {
      List<Skill> skillList = new LinkedList<>();
      for (Map<String, Skill> skillMap : registeredSkillList.values()) {
        skillList.addAll(skillMap.values());
      }
      out.println(mapper.writerWithDefaultPrettyPrinter().writeValueAsString(skillList));
    } catch (Exception ex) {
      ex.printStackTrace();
      log.error("failed storing skills to file - e:", ex);
    }
  }

  public static void loadSkillDB() {
    if (true) return;

    try {
      String skill_json = new String(Files.readAllBytes(Paths.get(SKILL_DB_FILENAME)));
      List<Skill> skillList = mapper.readValue(skill_json, new TypeReference<List<Skill>>() {
      });
      for (Skill skill : skillList) {
        if (registeredSkillList.get(skill.getName()) == null) {
          Map<String, Skill> skillMap = new HashMap<>();
          skillMap.put(skill.getId(), skill);
          registeredSkillList.put(skill.getName(), skillMap);

        } else {
          Map<String, Skill> skillMap = registeredSkillList.get(skill.getName());
          skillMap.put(skill.getId(), skill);
          registeredSkillList.put(skill.getName(), skillMap);
        }
      }

    } catch (Exception e) {
      log.error("Unable to parse skill db - e:{}", e);
    }


  }

  public static int getSkillDBSize() {
    List<Skill> skillList = new LinkedList<>();
    for (Map<String, Skill> skillMap : registeredSkillList.values()) {
      skillList.addAll(skillMap.values());
    }
    return skillList.size();
  }

}
