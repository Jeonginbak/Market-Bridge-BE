package com.objects.marketbridge.domains.member.controller;

import com.objects.marketbridge.common.responseobj.ApiResponse;
import com.objects.marketbridge.common.security.annotation.AuthMemberId;
import com.objects.marketbridge.domains.member.dto.*;
import com.objects.marketbridge.domains.member.service.MemberService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/member")
public class MemberController {

    private final MemberService memberService;


    @GetMapping("/address")
    public ApiResponse<List<GetAddressesResponse>> findAddrress(@AuthMemberId Long memberId){
        List<GetAddressesResponse> addressesResponses =memberService.findByMemberId(memberId);
        return ApiResponse.ok(addressesResponses);
    }

    @PostMapping("/address")
    public ApiResponse<List<GetAddressesResponse>> addAddressValue(
            @AuthMemberId Long memberId,
            @Valid @RequestBody AddAddressRequestDto request){
        List<GetAddressesResponse> addressesResponses = memberService.addMemberAddress(memberId,request);
       return ApiResponse.ok(addressesResponses);
    }

    @PatchMapping("/address/{addressId}")
    public ApiResponse<List<GetAddressesResponse>> updateAddress(
            @AuthMemberId Long memberId ,@Valid @RequestBody AddAddressRequestDto request ,
            @PathVariable (name = "addressId") Long addressId){
        List<GetAddressesResponse> addressesResponses = memberService.updateMemberAddress(memberId,addressId,request);
        return ApiResponse.ok(addressesResponses);
    }

    @DeleteMapping("/address/{addressId}")
    public ApiResponse<List<GetAddressesResponse>> deleteAddress(
            @AuthMemberId Long memberId ,@PathVariable (name = "addressId")  Long addressId){
        List<GetAddressesResponse> addressesResponses = memberService.deleteMemberAddress(memberId,addressId);
        return ApiResponse.ok(addressesResponses);
    }

    @GetMapping("/email-check")
    public ApiResponse<CheckedResultDto> checkDuplicateEmail(@RequestParam(name="email") String email) {
        CheckedResultDto checkedResultDto = memberService.isDuplicateEmail(email);
        return ApiResponse.ok(checkedResultDto);
    }

    //Wishlist 시작
    @GetMapping("/wishlist-check")
    public ApiResponse <Boolean> checkWishlistByMemberid(
            @AuthMemberId Long memberId,
            @RequestBody WishlistRequest request){
        Boolean isWishlist = memberService.checkWishlist(memberId, request);
        return ApiResponse.ok(isWishlist);
    }

    @DeleteMapping("/wishlist")
    public ApiResponse<String> deleteWishlist (
            @AuthMemberId Long memberId,
            @RequestBody WishlistRequest request){
        memberService.deleteWishlist(memberId,request);
        return ApiResponse.of(HttpStatus.OK);
    }

    @GetMapping("/wishlist")
    public ApiResponse<Slice<WishlistResponse>> findWishlistByMemberId(
            @AuthMemberId Long memberId,
            @PageableDefault(value = 5, sort = {"createdAt"}, direction = Sort.Direction.DESC)Pageable pageable){
        Slice<WishlistResponse> wishlistResponse= memberService.findWishlistById(pageable,memberId);
        return ApiResponse.ok(wishlistResponse);
    }

    @PostMapping("/wishlist")
    public ApiResponse<String> addWish(
            @AuthMemberId Long memberId ,
            @RequestBody WishlistRequest request){
        memberService.addWish(memberId,request);
        return ApiResponse.create();
    }


    @GetMapping("/account-email")
    public ApiResponse<MemberEmail> getEmail(@AuthMemberId Long memberId) {
        MemberEmail memberEmail = memberService.getEmail(memberId);
        return ApiResponse.ok(memberEmail);
    }

    @GetMapping("/account-info")
    public ApiResponse<GetMemberInfo> getMemberInfo (@AuthMemberId Long memberId, @RequestParam(name="password") String password) {
        GetMemberInfo getMemberInfo = memberService.getMemberInfo(memberId, password);
        return ApiResponse.ok(getMemberInfo);
    }

    @PatchMapping("/account-info")
    public ApiResponse<Void> updateMemberInfo(@AuthMemberId Long memberId, @Valid @RequestBody UpdateMemberInfo updateMemberInfo) {
        memberService.updateMemberInfo(memberId, updateMemberInfo);
        return ApiResponse.ok(null);
    }

    @GetMapping("/email-find")
    public ApiResponse<MemberEmail> findMemberEmail(@RequestParam(name="name") String name, @RequestParam(name="phoneNo") String phoneNo) {
        MemberEmail memberEmail = memberService.findMemberEmail(name, phoneNo);
        return ApiResponse.ok(memberEmail);
    }

    @GetMapping("/password-find")
    public ApiResponse<MemberId> findMemberId(@RequestParam(name="name") String name, @RequestParam(name="email") String email) {
        MemberId memberId = memberService.findMemberId(name, email);
        return ApiResponse.ok(memberId);
    }

    @PatchMapping("/password-reset")
    public ApiResponse<Void> updatePassword(@Valid @RequestBody UpdatePassword updatePassword) {
        memberService.updatePassword(updatePassword);
        return ApiResponse.ok(null);
    }


}



