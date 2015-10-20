package com.yu.test.ssl;

import java.io.FileInputStream;
import java.security.KeyStore;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.util.Enumeration;

public class TestX509 {

	public static void main( String[] args ) {
		final String KEYSTORE_FILE = "/cert/release/PM_700000000000001_acp.pfx";
		final String KEYSTORE_PASSWORD = "000000";
		final String KEYSTORE_ALIAS = "alias";

		try
		{
			KeyStore ks = KeyStore.getInstance( "PKCS12" );
			FileInputStream fis = new FileInputStream( KEYSTORE_FILE );

			// If the keystore password is empty(""), then we have to set
			// to null, otherwise it won't work!!!
			char[] nPassword = null;
			if( ( KEYSTORE_PASSWORD == null ) || KEYSTORE_PASSWORD.trim().equals( "" ) )
			{
				nPassword = null;
			}
			else
			{
				nPassword = KEYSTORE_PASSWORD.toCharArray();
			}
			ks.load( fis, nPassword );
			fis.close();

			System.out.println( "keystore type=" + ks.getType() );

			// Now we loop all the aliases, we need the alias to get keys.
			// It seems that this value is the "Friendly name" field in the
			// detals tab <-- Certificate window <-- view <-- Certificate
			// Button <-- Content tab <-- Internet Options <-- Tools menu
			// In MS IE 6.
			Enumeration<String> en = ks.aliases();
			String keyAlias = null;
			if( en.hasMoreElements() ) // we are readin just one certificate.
			{
				keyAlias = en.nextElement();
				System.out.println( "alias=[" + keyAlias + "]" );
			}

			// Now once we know the alias, we could get the keys.
			System.out.println( "is key entry=" + ks.isKeyEntry( keyAlias ) );
			PrivateKey prikey = ( PrivateKey )ks.getKey( keyAlias, nPassword );
			java.security.cert.Certificate cert = ks.getCertificate( keyAlias );
			PublicKey pubkey = cert.getPublicKey();

			System.out.println( "cert class = " + cert.getClass().getName() );
			System.out.println( "cert = " + cert );
			System.out.println( "public key = " + pubkey );
			System.out.println( "private key = " + prikey );
		} catch( Exception e )
		{
			e.printStackTrace();
		}
	}
}
