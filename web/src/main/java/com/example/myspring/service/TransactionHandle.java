package com.example.myspring.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


public abstract class TransactionHandle<T> {

    @Autowired
    protected PlatformTransactionManager transactionManager;


    private List<TransactionStatus> transactionStatuses = Collections.synchronizedList(new ArrayList<TransactionStatus>());

    protected abstract void process(T t);

    protected void runWithTransaction(T data){
        // 使用这种方式将事务状态都放在同一个事务里面
        DefaultTransactionDefinition def = new DefaultTransactionDefinition();
        // 事物隔离级别，开启新事务
        def.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRES_NEW);
        // 获得事务状态
        TransactionStatus status = transactionManager.getTransaction(def);
        transactionStatuses.add(status);
        process(data);
    }

    protected void commit() {
        for (TransactionStatus transactionStatus : transactionStatuses) {

            transactionManager.commit(transactionStatus);
        }
        transactionStatuses.clear();
    }

    protected void rollback() {
        for (TransactionStatus transactionStatus : transactionStatuses) {
            transactionManager.rollback(transactionStatus);
        }
        transactionStatuses.clear();
    }

}
