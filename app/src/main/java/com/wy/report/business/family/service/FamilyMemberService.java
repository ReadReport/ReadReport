package com.wy.report.business.family.service;

import com.wy.report.base.model.ResponseModel;
import com.wy.report.business.family.model.FamilyMemberModel;

import java.util.List;
import java.util.Map;

import retrofit2.http.Field;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;
import rx.Observable;

/**
 * @author cantalou
 * @date 2017年12月01日 14:18
 */
public interface FamilyMemberService {

    /**
     * 获取家庭成员列表
     *
     * @param mid
     * @return
     */
    @GET("/Member/get_family_member")
    Observable<ResponseModel<List<FamilyMemberModel>>> getFamilyMember(@Query("mid") long mid);


    /**
     * id:当前要修改的会员的id
     * name:成员姓名
     * sex：成员姓名
     * birthday：成员生日
     * relationship：成员关系
     * mobile:手机号码
     * id_card:证件号码（身份证等等）
     *
     * @return
     */
    @POST("/Member/edit_family_member")
    @FormUrlEncoded
    Observable<ResponseModel> editFamilyMember(@FieldMap Map<String, String> fields);

    /**
     * id:当前要修改的会员的id
     * name:成员姓名
     * sex：成员姓名
     * birthday：成员生日
     * relationship：成员关系
     * mobile:手机号码
     * id_card:证件号码（身份证等等）
     *
     * @return
     */
    @POST("/Member/edit_family_member")
    @FormUrlEncoded
    Observable<ResponseModel> editFamilyMember(@Field("id") long id, @Field("name") String name, @Field("sex") int sex, @Field("birthday") long birthday,
                                               @Field("relationship") String relationship, @Field("mobile") String mobile, @Field("id_card") String idCard);


    /**
     * id:当前要修改的会员的id
     * name:成员姓名
     * sex：成员姓名
     * birthday：成员生日
     * relationship：成员关系
     * mobile:手机号码
     * id_card:证件号码（身份证等等）
     *
     * @return
     */
    @POST("/Member/add_family_member")
    @FormUrlEncoded
    Observable<ResponseModel> addFamilyMember(@Field("id") long id, @Field("name") String name, @Field("sex") int sex, @Field("birthday") long birthday,
                                              @Field("relationship") String relationship, @Field("mobile") String mobile, @Field("id_card") String idCard);
}
