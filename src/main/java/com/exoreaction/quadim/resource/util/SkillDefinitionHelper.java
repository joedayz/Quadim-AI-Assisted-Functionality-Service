package com.exoreaction.quadim.resource.util;

import com.exoreaction.quadim.dto.skill.SimplifiedSkill;
import com.fasterxml.jackson.core.json.JsonReadFeature;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import java.io.File;
import java.nio.file.Files;
import java.util.LinkedList;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ClassPathResource;

public class SkillDefinitionHelper {

  public static final ObjectMapper mapper = new ObjectMapper().configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false)
      .configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false)
      .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
      .configure(JsonReadFeature.ALLOW_UNESCAPED_CONTROL_CHARS.mappedFeature(), true)
      .enable(JsonReadFeature.ALLOW_BACKSLASH_ESCAPING_ANY_CHARACTER.mappedFeature())
      .findAndRegisterModules();

  private static final Logger log = LoggerFactory.getLogger(SkillDefinitionHelper.class);

  public List<SimplifiedSkill> getStaticPublicSkillDefinitions() {
    try{
      File resource = new ClassPathResource(
          "skilldefinitions.json").getFile();
      String jsonSkillDefinitionList = new String(
          Files.readAllBytes(resource.toPath()));
      return  mapper.readValue(jsonSkillDefinitionList, new TypeReference<List<SimplifiedSkill>>() {});
      //return skillList;
    } catch (Exception e){
      log.error("Unable to initiate skilldefinitions",e);
    }
    return null;
  }

  public List<SimplifiedSkill> getEnhancedSkillDefinitions(List<SimplifiedSkill> simplifiedSkills) {
    int found_and_swapped = 0;
    int not_found = 0;
    List<SimplifiedSkill> enhancedSkillList = new LinkedList<>();
    for (SimplifiedSkill s : simplifiedSkills) {
      boolean found = false;

      if (s.getName() != null) {
        try {
          if (getStaticPublicSkillDefinitionsString().indexOf(s.getName()) > 0) {
            // We have a potential match
            for (SimplifiedSkill match : getStaticPublicSkillDefinitions()) {
              if (!found) {
                if ((match.getName().indexOf(s.getName()) > 0) || (s.getName().indexOf(match.getName()) > 0)) {
                  enhancedSkillList.add(match);
                  found_and_swapped++;
                  found = true;
                } else if ((match.toString().indexOf(s.getName()) > 0) || (s.getDescription_en() != null && s.getDescription_en().indexOf(s.getName()) > 0)) {
                  enhancedSkillList.add(match);
                  found_and_swapped++;
                  found = true;
                }
              }
            }
          }
        } catch (Exception e) {
          log.error("Trouble matching " + s.getName(),e);
        }
      }
      if (!found && s.getName_en() != null) {
        try {
          if (getStaticPublicSkillDefinitionsString().indexOf(s.getName_en()) > 0) {
            // We have a potential match
            for (SimplifiedSkill match : getStaticPublicSkillDefinitions()) {
              if (!found) {
                if ((match.getName_en().indexOf(s.getName_en()) > 0) || (s.getName_en().indexOf(match.getName_en()) > 0)) {
                  enhancedSkillList.add(match);
                  found_and_swapped++;
                  found = true;
                } else if ((match.toString().indexOf(s.getName_en()) > 0) || (s.getDescription_en() != null && s.getDescription_en().indexOf(s.getName_en()) > 0)) {
                  enhancedSkillList.add(match);
                  found_and_swapped++;
                  found = true;
                }
              }
            }
          }
        } catch (Exception e) {
          log.error("Trouble matching " + s.getName(),e);
        }

      }
      if (!found) {
        log.warn("Not Found skill:" + s.toString() + " -\n\n #not_found:" + ++not_found);

        enhancedSkillList.add(s);
      } else {
        log.info("Found skill:" + s.toString() + " -\n\n #found:" + found_and_swapped);
      }
    }
    return enhancedSkillList;
  }

  public  String getStaticPublicSkillDefinitionsString() {
    try{
      File resource = new ClassPathResource(
          "skilldefinitions.json").getFile();
      String jsonSkillDefinitionList = new String(
          Files.readAllBytes(resource.toPath()));
      return  jsonSkillDefinitionList;
      //return skillList;
    } catch (Exception e){
      log.error("Unable to initiate skilldefinitions",e);
    }
    return null;
  }

  public static final String jsonSkillDefinition = "{\n" +
      "    \"name\": \"Security architecture\",\n" +
      "    \"description\": \"Security architecture is a unified security design that addresses the necessities and potential risks involved in a certain scenario or environment. It also specifies when and where to apply security controls. The design process is generally reproducible.\",\n" +
      "    \"isPublic\": false,\n" +
      "    \"currentVersion\": 1,\n" +
      "    \"additionaljsonproperties\": null,\n" +
      "    \"public\": false\n" +
      "  }";

}
