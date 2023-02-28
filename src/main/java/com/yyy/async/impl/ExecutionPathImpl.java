package com.yyy.async.impl;

import com.yyy.async.ExecutionPath;
import com.yyy.async.Task;

/**
 * ExecutionPathImpl
 *
 * @author Yan Yunyang
 * @date 2023/2/28 14:44
 */
public class ExecutionPathImpl implements ExecutionPath {
    @Override
    public void setMust(boolean must) {

    }

    @Override
    public boolean getMust() {
        return false;
    }

    @Override
    public void setSource(Task<?> task) {

    }

    @Override
    public Task<?> getSource() {
        return null;
    }

    @Override
    public void setTarget(Task<?> task) {

    }

    @Override
    public Task<?> getTarget() {
        return null;
    }
}
