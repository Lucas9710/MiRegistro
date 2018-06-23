package android.com.sirioibanes.activities;

import android.com.sirioibanes.R;
import android.com.sirioibanes.dtos.SocialNetwork;
import android.com.sirioibanes.utils.FeedbackUtils;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.HashMap;

public class SocialNetworksActivity extends AbstractActivity {

    private static final String EXTRA_LIST = "list";
    private View twitterContainer;
    private View facebookContainer;
    private View instagramContainer;
    private View webpageContainer;
    private View snapchatContainer;
    private View youtubeContainer;
    private HashMap<String, SocialNetwork> socialNetworks;

    public static Intent getIntent(@NonNull final Context context, @NonNull final HashMap<String, HashMap> socialNetwork) {
        final Intent intent = new Intent(context, SocialNetworksActivity.class);
        intent.putExtra(EXTRA_LIST, socialNetwork);

        return intent;
    }

    @Override
    protected void onCreate(@Nullable final Bundle savedInstanceState) {

        // ACCIONES DEFAULT PREPARATORIAS
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_socialnetworks);
        if (getIntent().getExtras() == null) {
            throw new AssertionError("This Activity should be launched using it's factory method");
        }

        //ACCIONES NUESTRAS


        initializeVariables();  //inicializo mis variables para poder usarlas en los metodos siguientes
        hideAllSections(); // oculto todas las redes sociales para que por default aparezcan apagadas
        turnOnSocialNetworksWhenNeeded(); //prender las redes sociales que corresponda
    }

    public void initializeVariables() {
        // ASIGNO A LAS VARIABLES EL DATO DE LA VISTA
        twitterContainer = findViewById(R.id.containerTwitter);
        facebookContainer = findViewById(R.id.containerFacebook);
        instagramContainer = findViewById(R.id.containerInstagram);
        snapchatContainer = findViewById(R.id.containerSnapchat);
        webpageContainer = findViewById(R.id.containerWebpage);
        youtubeContainer = findViewById(R.id.containerYoutube);
        socialNetworks = (HashMap<String, SocialNetwork>) getIntent().getExtras().getSerializable(EXTRA_LIST);
    }

    public void hideAllSections() {
        // ESTO PONE LOS TRES BOTONES OCULTOS
        twitterContainer.setVisibility(View.GONE);
        facebookContainer.setVisibility(View.GONE);
        instagramContainer.setVisibility(View.GONE);
        snapchatContainer.setVisibility(View.GONE);
        webpageContainer.setVisibility(View.GONE);
        youtubeContainer.setVisibility(View.GONE);
    }

    public void turnOnSocialNetworksWhenNeeded() {


        // INTENTAMOS PRENDER EL BOTON DE TWITTER
        for (int i = 0; i < socialNetworks.keySet().size(); i++) {
            final String key = (String) socialNetworks.keySet().toArray()[i];
            final HashMap<String, String> socialNetwork = socialNetworks.get(key);
            String link = socialNetwork.get("link");
            String name = socialNetwork.get("name");

            if (key.equals("twitter")) {
                turnOnTwitter(link, name);
            }

            if (key.equals("facebook")) {
                turnOnFacebook(link, name);
            }

            if (key.equals("instagram")) {
                turnOnInstagram(link, name);
            }

            if (key.equals("webpage")) {
                turnOnWebpage(link, name);
            }

            if (key.equals("snapchat")) {
                turnOnSnapchat(link, name);
            }

            if (key.equals("youtube")) {
                turnOnYoutube(link, name);
            }
        }
    }

    private void turnOnTwitter(final String link, String name) {
        twitterContainer.setVisibility(View.VISIBLE);
        twitterContainer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                handleLink(link);
            }
        });

        final TextView myView = findViewById(R.id.buttonTwitter);
        myView.setText(name);
    }

    private void turnOnFacebook(final String link, String name) {
        facebookContainer.setVisibility(View.VISIBLE);
        facebookContainer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                handleLink(link);
            }
        });

        final TextView myView = findViewById(R.id.buttonFacebook);
        myView.setText(name);
    }

    private void turnOnInstagram(final String link, String name) {
        instagramContainer.setVisibility(View.VISIBLE);
        instagramContainer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                handleLink(link);
            }
        });

        final TextView myView = findViewById(R.id.buttonInstagram);
        myView.setText(name);
    }

    private void turnOnSnapchat(final String link, String name) {
        snapchatContainer.setVisibility(View.VISIBLE);
        snapchatContainer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                handleLink(link);
            }
        });

        final TextView myView = findViewById(R.id.buttonSnapchat);
        myView.setText(name);
    }

    private void turnOnWebpage(final String link, String name) {
        webpageContainer.setVisibility(View.VISIBLE);
        webpageContainer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                handleLink(link);
            }
        });

        final TextView myView = findViewById(R.id.buttonWebpage);
        myView.setText(name);
    }

    private void turnOnYoutube(final String link, String name) {
        youtubeContainer.setVisibility(View.VISIBLE);
        youtubeContainer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                handleLink(link);
            }
        });

        final TextView myView = findViewById(R.id.buttonYoutube);
        myView.setText(name);
    }

    private void handleLink(final String link) {
        try {
            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(link)));
        } catch (ActivityNotFoundException e) {
            FeedbackUtils.displaySnackbarError(findViewById(R.id.rootView), "El link es inválido");
        }
    }

    @Override
    protected boolean shouldValidate() {
        return false;
    }
}
