package cn.tpson.kulu.common.jpa.support;

import org.springframework.data.domain.Example;
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
public class ExampleLikeRangeSpecification<T> implements Specification<T> {
    private List<Range> ranges;
    private Example<T> example;
    private Like like;

    public ExampleLikeRangeSpecification(Example<T> example, Like like, List<Range> ranges) {
        this.example = example;
        this.like = like;
        this.ranges = ranges;
    }

    public static <T> ExampleLikeRangeSpecification<T> of(Example<T> example, Like like, Range... ranges) {
        return new ExampleLikeRangeSpecification<>(example, like, Arrays.asList(ranges));
    }

    @Override
    public Predicate toPredicate(Root<T> root, CriteriaQuery<?> query, CriteriaBuilder builder) {
        List<Predicate> list = new ArrayList<>();

        if (example != null) {
            Predicate p = ExampleSpecification.of(example).toPredicate(root, query, builder);
            if (p.getExpressions().size() > 0) {
                list.add(p);
            }
        }
        if (ranges != null && !ranges.isEmpty()) {
            Range[] array = new Range[ranges.size()];
            ranges.toArray(array);
            Predicate p = RangeSpecification.of(array).toPredicate(root, query, builder);
            if (p.getExpressions().size() > 0) {
                list.add(p);
            }
        }
        if (like != null) {
            Predicate p = LikeSpecification.of(like).toPredicate(root, query, builder);
            if (p.getExpressions().size() > 0) {
                list.add(p);
            }
        }

        Predicate[] array = new Predicate[list.size()];
        return list.isEmpty()
                ? builder.conjunction()
                : builder.and(list.toArray(array));
    }
}
