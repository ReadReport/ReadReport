package com.wy.report.base.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.hwangjr.rxbus.Bus;
import com.hwangjr.rxbus.RxBus;
import com.wy.report.R;

/*
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed To in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 *
 * @author cantalou
 * @date 2017-11-23 22:22
 */
public class BaseActivity extends AppCompatActivity {

    protected Bus rxBus;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        rxBus = RxBus.get();
        if (!rxBus.hasRegistered(this)) {
            rxBus.register(this);
        }
    }

    @Override
    protected void onDestroy() {
        if (rxBus != null && rxBus.hasRegistered(this)) {
            rxBus.unregister(this);
        }
        super.onDestroy();
    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.navigtor_pop_right_in, R.anim.navigtor_pop_right_out);
    }
}
