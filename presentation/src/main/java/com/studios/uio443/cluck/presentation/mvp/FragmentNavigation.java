package com.studios.uio443.cluck.presentation.mvp;

/**
 * Created by zundarik
 */

public interface FragmentNavigation {
    interface View {
        void atachPresenter(Presenter presenter);
    }

    interface Presenter {
			//void addFragment(BaseFragment fragment);
    }
}
