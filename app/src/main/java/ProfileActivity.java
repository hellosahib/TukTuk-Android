import android.os.Bundle;
import android.preference.PreferenceFragment;
import android.support.annotation.Nullable;

import tech.rtsproduction.tuktuk.R;

public class ProfileActivity extends AppCompatProfileActivity {

    //TODO: IMPLEMENT PROFILE SCREEN USING SHARED PREF

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getFragmentManager().beginTransaction().replace(android.R.id.content,new ProfileFragment(),null);
    }
    public static class ProfileFragment extends PreferenceFragment {
        @Override
        public void onCreate(@Nullable Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            addPreferencesFromResource(R.xml.profile_pref);
            //Name Preference Change Listener
        }

    }
}
