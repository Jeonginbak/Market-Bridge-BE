package com.objects.marketbridge.review.service.port;

import com.objects.marketbridge.review.domain.ReviewLike;
import org.springframework.data.repository.query.Param;

public interface ReviewLikeRepository {
    Boolean existsByReviewIdAndMemberId(Long reviewId, Long memberId);

    void save(ReviewLike reviewLike);

    void deleteByReviewIdAndMemberId(Long reviewId, Long memberId);

//    void deleteByReviewId(@Param("reviewId") Long reviewId);
//    ReviewLike findByReviewIdAndMemberId(Long reviewId, Long memberId);
//
//
//    Page<ReviewLike> findAllByReview_Product_Id(Long productId, Pageable pageable);
//
//    List<Long> findReviewIdsByProductIdOrderByLikedCount(Long productId);
//
//    Page<ReviewLike> findAllByReview_Member_Id(Long memberId, Pageable pageable);
//
//    void delete(ReviewLike reviewLike);
//
//    void deleteById(Long reviewLikesId);
//
//    void deleteByReviewId(Long reviewId);
//
//    Long countByReviewIdAndLikedIsTrue(Long reviewId);
//
//    Boolean existsByReviewIdAndMemberId(Long reviewId, Long memberId);
//
//    List<ReviewLike> findAllByReviewId(Long reviewId);
//
//    void deleteAllByReviewId(Long reviewId);
}
