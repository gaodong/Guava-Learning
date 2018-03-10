package basic.ordering;

import com.google.common.collect.Lists;
import com.google.common.collect.Ordering;
import com.google.common.primitives.Ints;

import java.util.Comparator;
import java.util.List;
import static com.google.common.collect.Ordering.natural;
import static com.google.common.collect.Ordering.usingToString;

/**
 * 排序器
 *
 * @author yuxuan
 * @create 2018-03-01 下午9:40
 **/
public class OrderingExplained {

    static List<People> peopleList = Lists.newArrayList(
            new People("peter", 8),
            new People("jerry", 9),
            new People("harry", 10),
            new People("eva", 11),
            new People("vermouth", 12),
            new People("jetty", 12));

    /**
     * 常见的排序器可以由下面的静态方法创建
     */
    public static void creationMethod(){

        List<String> list = Lists.newArrayList();
        list.add("peter");
        list.add("jerry");
        list.add("harry");
        list.add("eva");
        list.add("john");
        list.add("neron");
        System.out.println("list:"+ list);

        // 对可排序类型做自然排序，如数字按大小，日期按先后排序
        Ordering<String> naturalOrdering = natural();
        // 按对象的字符串形式做字典排序
        Ordering<Object> usingToStringOrdering = usingToString();
        // 把给定的Comparator转化为排序器
        Ordering<String> fromOrdering = Ordering.from(new Comparator<String>() {
            public int compare(String o1, String o2) {
                return o1.hashCode() - o2.hashCode() ;
            }
        });
        // 直接继承Ordering
        Ordering<String> byLengthOrdering = new Ordering<String>() {
            public int compare(String left, String right) {
                return Ints.compare(left.length(), right.length());
            }
        };

        System.out.println("naturalOrdering:"+ naturalOrdering.sortedCopy(list));
        System.out.println("usingToStringOrdering:"+ usingToStringOrdering.sortedCopy(list));
        System.out.println("fromOrdering:"+ fromOrdering.sortedCopy(list));
        System.out.println("byLengthOrdering:"+ byLengthOrdering.sortedCopy(list));

    }

    /**
     * 链式调用方法
     * 通过链式调用，可以由给定的排序器衍生出其它排序器
     */
    public static void chainingMethod(){
        List<String> list = Lists.newArrayList();
        list.add("peter");
        list.add("jerry");
        list.add("harry");
        list.add("eva");
        list.add("john");
        System.out.println("list:"+ list);

        // 对可排序类型做自然排序，如数字按大小，日期按先后排序
        Ordering<String> naturalOrdering = natural();
        // 获取语义相反的排序器
        Ordering<String> reverseOrdering = natural().reverse();
        // 使用当前排序器，但额外把null值排到最前面
        natural().nullsFirst();
        // 使用当前排序器，但额外把null值排到最后面
        natural().nullsLast();
        // 合成另一个比较器，以处理当前排序器中的相等情况
        Ordering<People> secondaryOrdering = new PeopleAgeOrder().compound(new PeopleNameLengthOrder());

        System.out.println("naturalOrdering:"+ naturalOrdering.sortedCopy(list));
        System.out.println("reverseOrdering:"+ reverseOrdering.sortedCopy(list));
        System.out.println("secondaryOrdering:"+ secondaryOrdering.sortedCopy(peopleList));
    }

    /**
     * 运用排序器
     * Guava的排序器实现有若干操纵集合或元素值的方法
     */
    public static void applicationMethod() {
        List<String> list = Lists.newArrayList();
        list.add("peter");
        list.add("jerry");
        list.add("harry");
        list.add("eva");
        list.add("john");
        System.out.println("list:"+ list);

        // 对可排序类型做自然排序，如数字按大小，日期按先后排序
        Ordering<String> naturalOrdering = natural();

        System.out.println("naturalOrdering:"+ naturalOrdering.sortedCopy(list));
        // 获取可迭代对象中最大的k个元素
        System.out.println("greatestOfOrdering:"+ naturalOrdering.greatestOf(list, 3));

        // 判断可迭代对象是否已按排序器排序
        // 允许有排序值相等的元素
        System.out.println("isOrdered:"+ naturalOrdering.isOrdered(list));

        naturalOrdering.sortedCopy(list);
    }

    public static void main(String args[]) {
        creationMethod();
        chainingMethod();
        applicationMethod();

    }
}
