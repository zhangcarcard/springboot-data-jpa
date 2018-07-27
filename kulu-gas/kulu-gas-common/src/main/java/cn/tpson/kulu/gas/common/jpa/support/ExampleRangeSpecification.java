package cn.tpson.kulu.gas.common.jpa.support;

import org.springframework.data.domain.Example;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.Arrays;
import java.util.List;

/**
 * Created by wangyunfei on 2017/6/6.
 */
public class ExampleRangeSpecification<T> implements Specification<T> {
    private final List<Range> ranges;
    private Example<T> example;

    public ExampleRangeSpecification(Example<T> example, List<Range> ranges) {
        this.example = example;
        this.ranges = ranges;
    }

    public static <T> ExampleRangeSpecification<T> of(Example<T> example, Range... ranges) {
        return new ExampleRangeSpecification(example, Arrays.asList(ranges));
    }

    public ExampleRangeSpecification add(Range range) {
        this.ranges.add(range);
        return this;
    }

    @Override
    public Predicate toPredicate(Root<T> root, CriteriaQuery<?> query, CriteriaBuilder builder) {
        Range[] array = new Range[ranges.size()];
        ranges.toArray(array);

        Predicate p1 = ExampleSpecification.of(example).toPredicate(root, query, builder);
        Predicate p2 = RangeSpecification.of(array).toPredicate(root, query, builder);
        return builder.and(p1, p2);
    }
}
