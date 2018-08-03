package cn.tpson.kulu.common.jpa.support;

import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by wangyunfei on 2017/6/6.
 */
public class LikeSpecification<T> implements Specification<T> {
    private final Like like;

    public LikeSpecification(Like like) {
        this.like = like;
    }

    public static LikeSpecification of(Like like) {
        return new LikeSpecification(like);
    }

    @Override
    public Predicate toPredicate(Root<T> root, CriteriaQuery<?> query, CriteriaBuilder builder) {
        List<Predicate> predicates = new ArrayList<>();

        if (like != null) {
            for (Like.Pair pair : like.getPairs()) {
                Predicate p = builder.like(root.get(pair.getField()), pair.getPattern());
                predicates.add(p);
            }
        }

        Predicate[] array = new Predicate[predicates.size()];
        return predicates.isEmpty()
                ? builder.conjunction()
                : builder.or(predicates.toArray(array));
    }
}
