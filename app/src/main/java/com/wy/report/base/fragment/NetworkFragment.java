package com.wy.report.base.fragment;

import android.support.annotation.CallSuper;
import android.view.View;

import com.wy.report.R;
import com.wy.report.base.dialog.CommonProgressDialog;

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
 * @date 2017-11-24 23:54
 */
public abstract class NetworkFragment extends ToolbarFragment {

    protected CommonProgressDialog progressDialog;

    @Override
    @CallSuper
    protected void initView(View contentView) {
        super.initView(contentView);
        progressDialog = new CommonProgressDialog(getActivity(), R.style.AppProgressDialog);
    }

    public void handleStart() {
        if (progressDialog != null) {
            progressDialog.show();
        }
    }

    public void handleError(Throwable t) {
        if (progressDialog != null) {
            progressDialog.dismiss();
        }
    }

    public void handleSuccess(Object o) {
        if (progressDialog != null) {
            progressDialog.dismiss();
        }
    }

    protected void loadData() {

    }
}
