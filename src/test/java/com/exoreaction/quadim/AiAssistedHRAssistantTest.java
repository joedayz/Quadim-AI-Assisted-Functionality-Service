package com.exoreaction.quadim;

import static java.time.Duration.ofSeconds;

import com.exoreaction.quadim.service.ApiKeys;
import com.fasterxml.jackson.core.json.JsonReadFeature;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import dev.langchain4j.memory.chat.MessageWindowChatMemory;
import dev.langchain4j.model.chat.ChatLanguageModel;
import dev.langchain4j.model.openai.OpenAiChatModel;
import dev.langchain4j.model.openai.OpenAiModelName;
import dev.langchain4j.model.output.structured.Description;
import dev.langchain4j.service.AiServices;
import dev.langchain4j.service.MemoryId;
import dev.langchain4j.service.UserMessage;
import java.util.List;
import java.util.Random;
import org.junit.jupiter.api.Test;

public class AiAssistedHRAssistantTest {

  public static final ObjectMapper mapper = new ObjectMapper()
      .configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false)
      .configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false)
      .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
      .configure(JsonReadFeature.ALLOW_UNESCAPED_CONTROL_CHARS.mappedFeature(), true)
      .enable(JsonReadFeature.ALLOW_BACKSLASH_ESCAPING_ANY_CHARACTER.mappedFeature())
      .findAndRegisterModules();

  @Test
  public void testChatWithHRAI() throws Exception {
    Random r = new Random();
    int userNo = r.nextInt(100);
    // PLan
    ChatLanguageModel model = OpenAiChatModel.builder()
        .apiKey(ApiKeys.MY_OPENAI_API_KEY)
        .modelName(OpenAiModelName.GPT_3_5_TURBO)
        .timeout(ofSeconds(900))
        .temperature(0.9)
        .build();
    Assistant assistant = AiServices.builder(Assistant.class)
        .chatLanguageModel(model)
        .chatMemoryProvider(memoryId -> MessageWindowChatMemory.withMaxMessages(10))
        .build();

    // a) create types for retrieving skills and experience objects from responses
    SkillExtractor skillExtractor = AiServices.create(SkillExtractor.class, model);

    // b) simulate a chat
    String appendPrompt = "Answer acting as a friendly HR Consultant helping the user with his/her competence mapping, focussing on skills and projects."+
        "Structure the answer friendly and selling with bullets for discovered or suggested supporting skills and potential typical projects"+
        "where the user may have used those skills. " +
        "Limit answer to the most relevant 5 skills and top 8 projects";

    String q1 = "Yes, I do work with Java and java microservices on the backend ";
    System.out.println("me: " + q1);
    String res1 = assistant.chat(userNo, q1 + appendPrompt);
    System.out.println(res1);
    Skill extractedSkills1 = skillExtractor.extractSkillFrom(res1);
    System.out.println("\n\n1. Skill mapped:" + mapper.writerWithDefaultPrettyPrinter().writeValueAsString(extractedSkills1) + "\n\n");

  }

  interface Assistant {

    String chat(@MemoryId int memoryId, @UserMessage String userMessage);
  }

  static class Skill {

    @Description("the name of this skill")
    private String name;
    @Description("description of this skill. please make it selling and not more than 10 lines of text")
    private String description;
    @Description("list of suggested skills which correlate to this skill")
    private List<SkillReference> listOfCandidateSkillDefinitions;

    public String getName() {
      return name;
    }

    public void setName(String name) {
      this.name = name;
    }

    public String getDescription() {
      return description;
    }

    public void setDescription(String description) {
      this.description = description;
    }

    public List<SkillReference> getListOfCandidateSkillDefinitions() {
      return listOfCandidateSkillDefinitions;
    }

    public void setListOfCandidateSkillDefinitions(List<SkillReference> listOfCandidateSkillDefinitions) {
      this.listOfCandidateSkillDefinitions = listOfCandidateSkillDefinitions;
    }

    @Override
    public String toString() {
      return "Skill{" +
          "skillName='" + name + '\'' +
          ", skillDescription='" + description + '\'' +
          ", listOfCandidateSkillDefinitions=" + listOfCandidateSkillDefinitions +
          '}';
    }
  }

  static class SkillReference {

    @Description("the name of this skill")
    private String name;
    @Description("description of this skill. please make it selling and not more than 10 lines of text")
    private String description;

    public String getName() {
      return name;
    }

    public void setName(String name) {
      this.name = name;
    }

    public String getDescription() {
      return description;
    }

    public void setDescription(String description) {
      this.description = description;
    }


    @Override
    public String toString() {
      return "SkillReference{" +
          "skillName='" + name + '\'' +
          ", skillDescription='" + description + '\'' +
          '}';
    }
  }

  interface SkillExtractor {

    @UserMessage("Extract information about a skill from {{it}}")
    Skill extractSkillFrom(String text);
  }


}
