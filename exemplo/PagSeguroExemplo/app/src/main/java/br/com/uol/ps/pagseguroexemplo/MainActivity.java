package br.com.uol.ps.pagseguroexemplo;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBarActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.math.BigDecimal;

import br.com.uol.ps.library.PagSeguro;
import br.com.uol.ps.library.PagSeguroRequest;
import br.com.uol.ps.library.PagSeguroResponse;

/**
 * @author Jean Rodrigo Dalbon Cunha
 *         cin_jcunha@uolinc.com
 */
public class MainActivity extends ActionBarActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container, new PlaceholderFragment())
                    .commit();
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        PagSeguro.onActivityResult(this, requestCode, resultCode, data);
    }

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {
        Button btnCallPayment;
        private EditText edtPaymentValue;


        public PlaceholderFragment() {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_main, container, false);

            btnCallPayment = (Button) rootView.findViewById(R.id.button_call_payment);
            btnCallPayment.setOnClickListener(payWithPagSeguro());
            edtPaymentValue = (EditText) rootView.findViewById(R.id.payment_value);


            return rootView;
        }

        private View.OnClickListener payWithPagSeguro() {
            return new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    BigDecimal amount = new BigDecimal(edtPaymentValue.getText().toString());
                    //quantidade de parcelas
                    int quantityParcel = 1;
                    PagSeguro.pay(new PagSeguroRequest()
                                    .withNewItem("Item Description", quantityParcel, amount)
                                    .withVendorEmail("seu email cadastrado no pagseguro")
                                    .withBuyerEmail("comprador@mail.com.br")
                                    .withBuyerCPF("00000000000")
                                    .withBuyerCellphoneNumber("5511000000000")
                                    .withReferenceCode("123")
                                    .withEnvironment(PagSeguro.Environment.PRODUCTION)
                                    .withAuthorization("seu email de login no pagseguro", "codigo obtido no pagseguro.uol.com.br"),

                            getActivity(),
                            R.id.container,
                            new PagSeguro.PagSeguroListener() {
                                @Override
                                public void onSuccess(PagSeguroResponse response, Context context) {
                                    Toast.makeText(context, "Lib PS retornou pagamento aprovado! " + response, Toast.LENGTH_LONG).show();
                                }

                                @Override
                                public void onFailure(PagSeguroResponse response, Context context) {
                                    Toast.makeText(context, "Lib PS retornou FALHA no pagamento! " + response, Toast.LENGTH_LONG).show();
                                }
                            });

                }
            };
        }

    }
}
