package com.luizgadao.sharetextandimage;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import java.util.List;


public class MainActivity extends ActionBarActivity {

    private ImageView imageView;

    @Override
    protected void onCreate( Bundle savedInstanceState ) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_main );

        imageView = ( ImageView ) findViewById( R.id.imageview );
        imageView.setImageResource( R.drawable.android );

        Button btShareText = ( Button ) findViewById( R.id.bt_share_text );
        Button btShareImage = ( Button ) findViewById( R.id.bt_share_image );

        btShareText.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick( View v ) {
                shareText();
            }
        } );

        btShareImage.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick( View v ) {
                //shareImage();
                customShare();
            }
        } );
    }

    private void shareText() {
        // cria a intent e difine a ação
        Intent intent = new Intent( Intent.ACTION_SEND );
        // tipo de conteúdo da intent
        intent.setType( "text/plain" );
        // string a ser enviada para outra intent
        intent.putExtra( Intent.EXTRA_TEXT, "tutorial de como compartilhar text no android." );
        // inicia a intent
        startActivity( intent );
    }

    private void shareImage() {
        startActivity( createIntentShareImage() );
    }

    private Intent createIntentShareImage() {
        StringBuilder strBuilder = new StringBuilder();
        strBuilder.append( "android.resource://" )
                // defina o seu package aqui
                .append( "com.luizgadao.sharetextandimage/" )
                //drawable a ser capturado, repare que esse é o drawable definido no src do ImageView
                .append( "drawable/android" );

        //captura a uri do drawable que esta no Imageview
        Uri uriImage = Uri.parse( strBuilder.toString() );
        // cria a intent e difine a ação
        Intent intent = new Intent( Intent.ACTION_SEND );
        // tipo de conteúdo da intent
        intent.setType( "image/*" );
        // stream a ser compartilhado
        intent.putExtra( Intent.EXTRA_STREAM, uriImage );
        return intent;
    }

    private void customShare(){
        // intent para capturar imagem
        Intent intent = createIntentShareImage();

        List<ResolveInfo> resolveInfos = getPackageManager().queryIntentActivities( intent, 0 );
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle( "Compartilhar com..." );
        final ShareIntentListAdapter adapter = new ShareIntentListAdapter(this, R.layout.item_share, resolveInfos);
        builder.setAdapter( adapter, new DialogInterface.OnClickListener() {
            @Override
            public void onClick( DialogInterface dialog, int which ) {
                ResolveInfo info = (ResolveInfo) adapter.getItem(which);
                Log.i( "on-click", info.activityInfo.packageName );
            }
        } );

        builder.create().show();
    }

}
