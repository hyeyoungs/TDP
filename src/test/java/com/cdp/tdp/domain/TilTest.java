//package com.cdp.tdp.domain;
//
//import com.cdp.tdp.dto.SignupRequestDto;
//import com.cdp.tdp.dto.TilRequestDto;
//import com.cdp.tdp.service.TilService;
//import com.fasterxml.jackson.databind.ObjectMapper;
//import com.google.gson.GsonBuilder;
//import org.junit.jupiter.api.*;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
//import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.http.HttpHeaders;
//import org.springframework.http.MediaType;
//import org.springframework.restdocs.RestDocumentationContextProvider;
//import org.springframework.restdocs.RestDocumentationExtension;
//import org.springframework.restdocs.payload.JsonFieldType;
//import org.springframework.test.context.ActiveProfiles;
//import org.springframework.test.context.junit.jupiter.SpringExtension;
//import org.springframework.test.web.servlet.MockMvc;
//import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
//import org.springframework.test.web.servlet.setup.MockMvcBuilders;
//import org.springframework.web.context.WebApplicationContext;
//import org.springframework.web.filter.CharacterEncodingFilter;
//
//import java.security.Principal;
//
//import static org.junit.jupiter.api.Assertions.*;
//import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
//import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.documentationConfiguration;
//import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.post;
//import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
//import static org.springframework.restdocs.payload.PayloadDocumentation.requestFields;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
//
//
//@ExtendWith({RestDocumentationExtension.class, SpringExtension.class}) // Extension 기능 사용
//@SpringBootTest(
//        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,
//
//)
//@AutoConfigureMockMvc
//@ActiveProfiles("test") // 사용할 properties 파일
//@AutoConfigureRestDocs // Rest Docs 대해 Auto Configuration 함
//@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
//@TestInstance(TestInstance.Lifecycle.PER_CLASS)
//class TilTest {
//    @Autowired
//    TilService tilservice;
//
//    @Autowired
//    protected MockMvc mockMvc;
//
//    @Autowired
//    ObjectMapper objectMapper;
//
//    @BeforeEach
//    public void setUp(WebApplicationContext webApplicationContext, RestDocumentationContextProvider restDocumentation) {
//        // API를 호출할 수 있는 상태를 만들어준다.
//        this.mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext)
//                .addFilters(new CharacterEncodingFilter("UTF-8", true))
//                .alwaysDo(MockMvcResultHandlers.print())
//                .apply(documentationConfiguration(restDocumentation)).build(); //api문서화 가능해짐
//    }
//
//    @Test
//    @Order(1)
//    @DisplayName("TIL 작성")
//    @WithUserDetails("manager@company.com")
//    public void createTil() throws Exception {
//        TilRequestDto tildto=new TilRequestDto();
//        Principal principal=new Principal() {
//            @Override
//            public String getName() {
//                return "test_principal";
//            }
//        };
//        tildto.setTilTitle("test1");
//        tildto.setTilContent("test1 content");
//        tildto.setTilView(true);
//        tildto.setTags("tags");
//        tildto.setUser_id("soo");
//        String jsonString = new GsonBuilder().setPrettyPrinting().create().toJson(tildto);
//
//        mockMvc.perform(post("/til").principal(principal)
//                .contentType(MediaType.APPLICATION_JSON)
//                .content(jsonString)
//                .accept(MediaType.APPLICATION_JSON))
//                .andExpect(status().isOk()) // ResultActions 객체가 리턴 값을 검증하고 확인할 수 있는 메소드
//                .andDo(document("/til/create", //다큐멘테이션의 이름지정
//                        requestFields(
//                                // create는 requestFields를 받기 때문에 문서에 requestFields을 명시하겠다는 의미
//                                fieldWithPath("tilTitle").description("제목"),
//                                fieldWithPath("tilContent").description("내용"),
//                                fieldWithPath("tilView").description("공개/비공개 여부"),
//                                fieldWithPath("tags").description("태그"),
//                                fieldWithPath("user_id").description("작성자"),
//                                fieldWithPath("til_like").description("좋아요 수").optional().type(JsonFieldType.NUMBER),
//                                fieldWithPath("num_comment").description("댓글 수").optional().type(JsonFieldType.NUMBER)
//
//                )));
//    }
//
//
//
//
//
//}
//
//
//
//
