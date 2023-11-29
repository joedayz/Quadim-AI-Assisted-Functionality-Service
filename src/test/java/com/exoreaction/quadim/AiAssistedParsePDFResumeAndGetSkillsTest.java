package com.exoreaction.quadim;

import static com.exoreaction.quadim.resource.util.SkillDefinitionHelper.jsonSkillDefinition;
import static java.time.Duration.ofSeconds;

import com.exoreaction.quadim.dto.skill.SimplifiedSkill;
import com.exoreaction.quadim.resource.util.SkillDefinitionHelper;
import com.exoreaction.quadim.service.ApiKeys;
import com.fasterxml.jackson.core.json.JsonReadFeature;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import dev.langchain4j.memory.chat.MessageWindowChatMemory;
import dev.langchain4j.model.chat.ChatLanguageModel;
import dev.langchain4j.model.openai.OpenAiChatModel;
import dev.langchain4j.model.openai.OpenAiModelName;
import dev.langchain4j.service.AiServices;
import dev.langchain4j.service.MemoryId;
import dev.langchain4j.service.UserMessage;
import java.io.File;
import java.io.IOException;
import java.lang.invoke.MethodHandles;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.pdfbox.Loader;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.junit.jupiter.api.Test;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;


public class AiAssistedParsePDFResumeAndGetSkillsTest {

  public static final ObjectMapper mapper = new ObjectMapper()
      .configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false)
      .configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false)
      .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
      .configure(JsonReadFeature.ALLOW_UNESCAPED_CONTROL_CHARS.mappedFeature(), true)
      .enable(JsonReadFeature.ALLOW_BACKSLASH_ESCAPING_ANY_CHARACTER.mappedFeature())
      .findAndRegisterModules();

  SkillDefinitionHelper helper;

  @Test
  public void testParseOSDResumeWithAIProducingSkillDefinitions() throws Exception {
    helper = new SkillDefinitionHelper();

    Map<String, List<SimplifiedSkill>> resultMap = new HashMap<>();
    // PLan
    ChatLanguageModel model = OpenAiChatModel.builder()
        .apiKey(ApiKeys.MY_OPENAI_API_KEY)
        .modelName(OpenAiModelName.GPT_4)
        .timeout(ofSeconds(900))
        .temperature(0.6)
        .build();
    Assistant assistant = AiServices.builder(Assistant.class)
        .chatLanguageModel(model)
        .chatMemoryProvider(memoryId -> MessageWindowChatMemory.withMaxMessages(10))
        .build();


    // a) create a prompt, specifying openAI to act as a friendly HR consultant trying to help the user discover his/her skills and experiences  PromptTemplate
    List<Resource> resourceList = getPDFResources();


    String appendPrompt = "Extract the users skills from this resume. Present the result as structured " +
        "json data in the following json format " + jsonSkillDefinition + " keep the name of the skill short";

    int n = 0;
    boolean RUN_FULL_REGRESSON = true;
    int found_and_swapped = 0;
    int not_found = 0;
    if (RUN_FULL_REGRESSON) {
      for (Resource resource : resourceList) {
        try {
          File file = resource.getFile();
          PDDocument document = Loader.loadPDF(file);
          PDFTextStripper stripper = new PDFTextStripper();
          String text = stripper.getText(document);
          //System.out.println(n + " Input data extracted from pdf resume:\n" + text + "\n\n");
          String res0 = assistant.chat(n, text + appendPrompt);
          //System.out.println(n + " Generated JSON SkillDefinitions:" + n++ + "\n\n" + res0 + "\n\n");
          List<SimplifiedSkill> simplifiedSkills = mapper.readValue(res0, new TypeReference<List<SimplifiedSkill>>() {
          });
          System.out.println("Mapped skill definitions:" + simplifiedSkills.size() + " for pdf:" + resource.getFilename() + "\n\n\n");
          List<SimplifiedSkill> enhancedSkillList = helper.getEnhancedSkillDefinitions(simplifiedSkills);
          resultMap.put(resource.getFilename(), simplifiedSkills);
          resultMap.put(resource.getFilename() + "-enhanced", enhancedSkillList);

        } catch (Exception e) {
          System.out.println("Exception handling " + resource.getFilename() + " - Stacktrace:" + Arrays.toString(e.getStackTrace()));
        }
      }
    }
    System.out.println(resultMap);

  }





  private List<Resource> getPDFResources() throws IOException {
    ClassLoader classLoader = MethodHandles.lookup().getClass().getClassLoader();
    PathMatchingResourcePatternResolver resolver = new PathMatchingResourcePatternResolver(
        classLoader);
    return new ArrayList<>(Arrays.asList(resolver.getResources("classpath:ExampleResumes/*.pdf")));
  }



  interface Assistant {

    String chat(@MemoryId int memoryId, @UserMessage String userMessage);

  }


}


