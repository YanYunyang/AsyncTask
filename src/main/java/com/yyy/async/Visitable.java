package com.yyy.async;

/**
 * Visitable
 *
 * @author Yan Yunyang
 * @date 2023/2/28 11:35
 */
public interface Visitable {
    String LF = "\n";

    String accept(final Visitor visitor);
}
