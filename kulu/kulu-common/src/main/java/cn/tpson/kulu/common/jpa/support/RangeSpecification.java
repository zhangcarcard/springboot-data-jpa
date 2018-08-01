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
public class RangeSpecification<T> implements Specification<T> {
    private final List<Range> ranges;

    public RangeSpecification(List<Range> ranges) {
        this.ranges = ranges;
    }

    public static RangeSpecification of(Range... ranges) {
        return new RangeSpecification(Arrays.asList(ranges));
    }

    public RangeSpecification add(Range range) {
        this.ranges.add(range);
        return this;
    }

    @Override
    public Predicate toPredicate(Root<T> root, CriteriaQuery<?> query, CriteriaBuilder builder) {
        List<Predicate> predicates = new ArrayList<>();

        for (Range range : ranges) {
            if (range.isBetween()) {
                Predicate rangePredicate = buildRangePredicate(range, root, builder);
                predicates.add(rangePredicate);
                predicates.add(builder.isNotNull(root.get(range.getField())));
            }
        }

        Predicate[] array = new Predicate[predicates.size()];
        return predicates.isEmpty()
                ? builder.conjunction()
                : builder.and(predicates.toArray(array));
    }

    private Predicate buildRangePredicate(Range range, Root<T> root, CriteriaBuilder builder) {
        if (range.isBetween()) {
            return builder.between(root.get(range.getField()), range.getLeft(), range.getRight());
        } else if (range.isLeftSet()) {
            return builder.greaterThanOrEqualTo(root.get(range.getField()), range.getLeft());
        } else if (range.isRightSet()) {
            return builder.lessThanOrEqualTo(root.get(range.getField()), range.getRight());
        }
        return null;
    }
}
