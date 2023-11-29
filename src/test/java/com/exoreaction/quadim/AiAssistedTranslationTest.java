package com.exoreaction.quadim;

import static java.time.Duration.ofSeconds;


import com.exoreaction.quadim.resource.util.SkillDefinitionHelper;
import com.exoreaction.quadim.service.ApiKeys;
import com.fasterxml.jackson.core.json.JsonReadFeature;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import dev.langchain4j.model.chat.ChatLanguageModel;
import dev.langchain4j.model.openai.OpenAiChatModel;
import dev.langchain4j.model.openai.OpenAiModelName;
import dev.langchain4j.service.MemoryId;
import dev.langchain4j.service.UserMessage;
import java.util.Arrays;
import org.junit.jupiter.api.Test;


public class AiAssistedTranslationTest {


    public static final ObjectMapper mapper = new ObjectMapper()
            .configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false)
            .configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false)
            .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
            .configure(JsonReadFeature.ALLOW_UNESCAPED_CONTROL_CHARS.mappedFeature(), true)
            .enable(JsonReadFeature.ALLOW_BACKSLASH_ESCAPING_ANY_CHARACTER.mappedFeature())
            .findAndRegisterModules();

    SkillDefinitionHelper helper;


    @Test
    public void testAIAssistedTranslationFromEnglishToNorwegian() throws Exception {


        // PLan
        ChatLanguageModel model = OpenAiChatModel.builder()
            .apiKey(ApiKeys.MY_OPENAI_API_KEY)
            .modelName(OpenAiModelName.GPT_3_5_TURBO_16K)
            .timeout(ofSeconds(900))
            .temperature(0.2)
            .build();


        String initialProjectDescription = "Chief Developer, Technical Architect: Developed several core modules in PasientSky's platform, " +
            "including communication module against Norwegian public health authorities, topology module for clinical diagnosis " +
            "(ICPC2, CDC10 and SNOWMED), product module (medicines and prescription goods) m.m. Technical architect, Quality assurer. ";



        int n = 343;

        try {

            String res0 = model.generate( "Translate " + initialProjectDescription + " from English to Norwegian");
            System.out.println(n + " Translated descriptions:" + n++ + "\n\n" + res0 + "\n\n");
        } catch (Exception e) {
            System.out.println("Exception handling  - Stacktrace:" + Arrays.toString(e.getStackTrace()));
        }
    }

    @Test
    public void testAIAssistedTranslationFromEnglishToSpanish() throws Exception {


        // PLan
        ChatLanguageModel model = OpenAiChatModel.builder()
            .apiKey(ApiKeys.MY_OPENAI_API_KEY)
            .modelName(OpenAiModelName.GPT_3_5_TURBO)
            .timeout(ofSeconds(20))
            .temperature(0.2)
            .maxTokens(1000)
            .build();



        String initialProjectDescription = "Chief Developer, Technical Architect: Developed several core modules in PasientSky's platform, " +
            "including communication module against Norwegian public health authorities, topology module for clinical diagnosis " +
            "(ICPC2, CDC10 and SNOWMED), product module (medicines and prescription goods) m.m. Technical architect, Quality assurer. ";



        int n = 343;

        try {

            String res0 = model.generate( "Translate " + initialProjectDescription + " from English to Spanish");
            System.out.println(n + " Translated descriptions:" + n++ + "\n\n" + res0 + "\n\n");
        } catch (Exception e) {
            System.out.println("Exception handling  - Stacktrace:" + Arrays.toString(e.getStackTrace()));
        }
    }


    interface Assistant {
        String chat(@MemoryId int memoryId, @UserMessage String userMessage);

    }
}
