
package com.codingbloom.firebasepractice.model.service.module

import com.codingbloom.firebasepractice.model.service.AccountService
import com.codingbloom.firebasepractice.model.service.StorageService
import com.codingbloom.firebasepractice.model.service.impl.AccountServiceImpl
import com.codingbloom.firebasepractice.model.service.impl.StorageServiceImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class ServiceModule {
  @Binds abstract fun provideAccountService(impl: AccountServiceImpl): AccountService
  @Binds abstract fun provideStorageService(impl: StorageServiceImpl): StorageService
}
