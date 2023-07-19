package com.example.routinely
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults.outlinedTextFieldColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.routinely.ui.theme.RoutinelyTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            RoutinelyTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background
                ) {
                    Greeting("Android")
                }
            }
        }
    }
}

//API = retrofit requisições de API
//GSON = consumir API


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    var isPasswordFilled by remember { mutableStateOf(false) }
    var isEmailFilled by remember { mutableStateOf(false) }
    var isEmailValid by remember { mutableStateOf(true) }
    Column(
        modifier = Modifier
            .padding(bottom = 16.dp, start = 16.dp, end = 16.dp)
            .fillMaxWidth()
            .fillMaxHeight(),
        content = {
            Column(
                modifier = Modifier
                    .weight(0.3f)
                    .fillMaxWidth()
                    .fillMaxHeight(),
                content = {
                    Image(
                        painter = painterResource(R.drawable.logo_horizontal),
                        contentDescription = "Image",
                        modifier = Modifier
                            .size(height = 150.dp, width = 250.dp)
                            .align(Alignment.CenterHorizontally)
                    )
                    textoCabecalho()
            })
            Column(
                modifier = Modifier
                    .clip(MaterialTheme.shapes.medium)
                    .background(Color(color = 0xff8f8ce7))
                    .weight(0.65f)
                    .fillMaxWidth()
                    .fillMaxHeight(),
                content = {
                    Column(
                        verticalArrangement = Arrangement.spacedBy(16.dp),
                        modifier = Modifier
                            .padding(16.dp)
                    ) {
                        Text(
                            text = "Acessar conta",
                            color = Color.White,
                            fontSize = 25.sp
                        )
                        txtLogin(onEmailChange = { email ->
                            isEmailFilled = email.isNotBlank()
                            isEmailValid = isValidEmailFormat(email)
                        })
                        txtSenha(onPasswordChange = { password ->
                            isPasswordFilled = password.isNotBlank()
                        })
                        chkLembrar()
                        btnFazerLogin(
                            onLoginClick = {
                                // Ação executada quando o botão de login é clicado
                                // Você pode verificar o estado do campo de senha aqui novamente, se necessário
                            },
                            emailPreenchido = isEmailFilled,
                            senhaPreenchida = isPasswordFilled,
                            isEmailValid = isEmailValid,
                        )
                        btnCadastro()
                        Divider()
                        btnGoogle()
                    }
            })
            //Espaço no final
            Column(modifier = Modifier.weight(0.05f)) {

            }
        }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun txtSenha(onPasswordChange: (String) -> Unit) {
    var password by rememberSaveable { mutableStateOf("") }
    OutlinedTextField(
        value = password,
        onValueChange = {
            password = it
            onPasswordChange(it)},
        label = {
            Text(
                text = "Senha",
                style = TextStyle(color = Color.White) // Definindo a cor do texto como branco
            )
        },
        colors = outlinedTextFieldColors(
            focusedBorderColor = Color.White,
            unfocusedBorderColor = Color.White
        ),
        visualTransformation = PasswordVisualTransformation(),
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
        modifier = Modifier.fillMaxWidth() // Preencher toda a largura disponível no Row
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun txtLogin(onEmailChange: (String) -> Unit) {
    var text by rememberSaveable { mutableStateOf("") }
    OutlinedTextField(
        value = text,
        onValueChange = {
            text = it
            onEmailChange(it)},
        label = {
            Text(
                text = "Login",
                style = TextStyle(color = Color.White) // Definindo a cor do texto como branco
            )
        },
        colors = outlinedTextFieldColors(
            focusedBorderColor = Color.White,
            unfocusedBorderColor = Color.White
        ),
        modifier = Modifier
            .fillMaxWidth() // Preencher toda a largura disponível no Row
    )
}
@Composable
fun chkLembrar(){
    val checkboxState = remember { mutableStateOf(false) }
    Row(
        modifier = Modifier
        .clickable {checkboxState.value = !checkboxState.value }
    )
        {
            Checkbox(
                checked = checkboxState.value, onCheckedChange = null,
                colors = CheckboxDefaults.colors(
                    checkedColor = Color(0xff8f8ce7),
                    uncheckedColor = Color.White,
                    checkmarkColor = Color.White
                ),
            )
            Text(
                text = "Lembrar meu acesso",
                color = Color.White
            )
        }
}
@Composable
fun btnFazerLogin(onLoginClick: () -> Unit, emailPreenchido: Boolean, senhaPreenchida: Boolean, isEmailValid: Boolean) {
    Button(
        enabled = senhaPreenchida && emailPreenchido && isEmailValid,
        onClick = onLoginClick,
        modifier = Modifier
            .fillMaxWidth(),
        colors = ButtonDefaults.buttonColors(Color.White)

    ) {
        Text(
            text = "Fazer Login", color = Color(0xff8f8ce7)
        )
    }
}

@Composable
fun btnCadastro() {
    Button(
        onClick = {
            // Ação executada quando o botão é clicado
        },
        modifier = Modifier
            .fillMaxWidth(),
        colors = ButtonDefaults.buttonColors(Color.White)
    ) {
        Text(
            text = "Cadastre-se", color = Color.Gray
        )
    }
}

@Composable
fun btnGoogle() {
    Button(
        onClick = {
            // Ação executada quando o botão é clicado
        },
        modifier = Modifier
            .fillMaxWidth(),
        colors = ButtonDefaults.buttonColors(Color.White)
    ) {
        Image(
            painter = painterResource(id = R.drawable.vector),
            contentDescription = "Vector",
            modifier = Modifier
                .width(width = 18.dp)
                .height(height = 18.dp)
        )
        Text(
            text = "Entrar com Google", color = Color.Gray
        )
    }
}

@Composable
fun textoCabecalho() {
    Text(
        text = buildAnnotatedString {
            withStyle(
                style = SpanStyle(
                    color = Color(0xff171a21), fontSize = 16.sp, fontWeight = FontWeight.Bold
                )
            ) { append("Gerencie") }
            withStyle(
                style = SpanStyle(
                    color = Color(0xff171a21), fontSize = 16.sp
                )
            ) { append(" suas tarefas,") }
            withStyle(
                style = SpanStyle(
                    color = Color(0xff171a21), fontSize = 16.sp, fontWeight = FontWeight.Bold
                )
            ) { append(" organize ") }
            withStyle(
                style = SpanStyle(
                    color = Color(0xff171a21), fontSize = 16.sp
                )
            ) { append("sua rotina e") }
            withStyle(
                style = SpanStyle(
                    color = Color(0xff171a21), fontSize = 16.sp, fontWeight = FontWeight.Bold
                )
            ) { append(" aproveite ") }
            withStyle(
                style = SpanStyle(
                    color = Color(0xff171a21), fontSize = 16.sp
                )
            ) { append("seu tempo com o que desejar.") }
        }
    )
}
fun isValidEmailFormat(email: String): Boolean {
    val emailRegex = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+".toRegex()
    return email.matches(emailRegex)
}
@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    RoutinelyTheme {
        Greeting("Android")
    }
}