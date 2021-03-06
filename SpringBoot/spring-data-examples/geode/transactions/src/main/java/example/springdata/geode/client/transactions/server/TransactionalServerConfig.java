/*
 * Copyright 2020-2021 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package example.springdata.geode.client.transactions.server;

import example.springdata.geode.client.transactions.Customer;

import org.apache.geode.cache.DataPolicy;
import org.apache.geode.cache.GemFireCache;
import org.apache.geode.cache.Scope;

import org.springframework.context.annotation.Bean;
import org.springframework.data.gemfire.ReplicatedRegionFactoryBean;
import org.springframework.data.gemfire.config.annotation.CacheServerApplication;
import org.springframework.data.gemfire.config.annotation.EnableLocator;
import org.springframework.data.gemfire.config.annotation.EnableManager;
import org.springframework.data.gemfire.transaction.config.EnableGemfireCacheTransactions;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * @author Patrick Johnson
 */
@EnableLocator
@EnableTransactionManagement
@EnableGemfireCacheTransactions
@EnableManager(start = true)
@CacheServerApplication(logLevel = "error")
public class TransactionalServerConfig {

	@Bean("Customers")
	ReplicatedRegionFactoryBean<Long, Customer> createCustomerRegion(GemFireCache gemfireCache) {
		ReplicatedRegionFactoryBean<Long, Customer> replicatedRegionFactoryBean = new ReplicatedRegionFactoryBean<>();
		replicatedRegionFactoryBean.setCache(gemfireCache);
		replicatedRegionFactoryBean.setRegionName("Customers");
		replicatedRegionFactoryBean.setDataPolicy(DataPolicy.REPLICATE);
		replicatedRegionFactoryBean.setScope(Scope.DISTRIBUTED_ACK);
		return replicatedRegionFactoryBean;
	}
}
