package com.doitua.test;

import com.doitua.doittest.activity.login.LoginActivityPresenter;
import com.doitua.doittest.activity.login.LoginActivityPresenterImpl;
import com.doitua.doittest.activity.login.LoginActivityView;
import com.doitua.doittest.model.PrefHelper;
import com.doitua.doittest.model.token.SignInResponse;
import com.doitua.doittest.retrofit.RetrofitService;
import com.doitua.doittest.util.SchedulerHelper;

import org.junit.Rule;
import org.junit.Test;
import org.mockito.InOrder;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;

import io.reactivex.Single;
import io.reactivex.observers.TestObserver;
import io.reactivex.schedulers.Schedulers;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.when;

/**
 * Created by batynchuk on 1/23/18.
 */
public class LoginTest {

    @Mock
    private RetrofitService retrofitService;

    @Mock
    private LoginActivityView view;

    @Mock
    private PrefHelper prefHelper;

    private String login = "email@test.com";
    private String password = "p@ssw0rd";

    private LoginActivityPresenter loginActivityPresenter;


    @Rule
    public MockitoRule mockitoRule = MockitoJUnit.rule();

    @Test
    public void testLoginSuccess() {

        when(retrofitService.login(login, password))
                .thenReturn(Single.just(new SignInResponse("", "", "")));

        loginActivityPresenter = new LoginActivityPresenterImpl(view, retrofitService, prefHelper,
                new SchedulerHelper(Schedulers.trampoline(), Schedulers.trampoline()));
        loginActivityPresenter.onLoginClick(login, password);

        InOrder inOrder = Mockito.inOrder(view);
        inOrder.verify(view, times(1)).startFeedActivity();

    }

    @Test
    public void testLoginError() {

        when(retrofitService.login(this.login, password)).thenReturn(Single.error(new Exception()));

        loginActivityPresenter = new LoginActivityPresenterImpl(view, retrofitService, prefHelper,
                new SchedulerHelper(Schedulers.trampoline(), Schedulers.trampoline()));
        loginActivityPresenter.onLoginClick(this.login, password);

        InOrder inOrder = Mockito.inOrder(view);
        inOrder.verify(view, times(1)).showError();

    }

    @Test
    public void subscribeTest() {
        when(retrofitService.login(this.login, password)).thenReturn(Single.error(new Exception()));
        TestObserver<SignInResponse> login = retrofitService.login(this.login, password).test();
        login.assertSubscribed();

    }


}
