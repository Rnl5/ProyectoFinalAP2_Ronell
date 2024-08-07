package com.edu.ucne.proyectofinalap2_ronell.presentation.Home

import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
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
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import com.edu.ucne.proyectofinalap2_ronell.R
import com.edu.ucne.proyectofinalap2_ronell.presentation.login.AuthState
import com.edu.ucne.proyectofinalap2_ronell.presentation.login.AuthViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Dashboard(
    irAInventario: () -> Unit,
    irACategoriaList: () -> Unit,
    irAMarcasList: () -> Unit,
    irAInventarioStockBajo: () -> Unit,
    irAInventarioFechaExp: () -> Unit,
    irAPerfilScreen: () -> Unit,
    irALogin: () -> Unit,
    authViewModel: AuthViewModel
    ) {

    val authState = authViewModel.authState.collectAsState()
    val userData by authViewModel.userData.collectAsState()



    LaunchedEffect(authState.value) {
        when(authState.value) {
            is AuthState.NoAutenticado -> irALogin()
            else -> Unit
        }
    }



    Column(
        modifier = Modifier
            .fillMaxHeight()
            .fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {


        ConstraintLayout {
            val (topIng, profile) = createRefs()
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(245.dp)
                    .constrainAs(topIng) {
                        top.linkTo(parent.top)
                        start.linkTo(parent.start)
                    }
                    .background(
                        color = Color(android.graphics.Color.parseColor("#5e3bee")),
                        shape = RoundedCornerShape(bottomEnd = 48.dp, bottomStart = 40.dp)
                    )
            )

            Row(
                modifier = Modifier
                    .padding(top = 48.dp, start = 24.dp, end = 24.dp)
                    .fillMaxWidth()
            ) {
                Column(
                    modifier = Modifier
                        .height(100.dp)
                        .padding(start = 14.dp)
                        .weight(8.7f),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.Start
                ) {
                    Text(
                        text = "Bienvenido/a",
                        color = Color.White,
                        fontSize = 18.sp,
                    )

                    userData?.let { usuario ->
                        Text(
                            text = usuario.nombreUsuario,
                            color = Color.White,
                            fontSize = 22.sp,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier.padding(top = 14.dp)
                        )

                    } ?: Text(text = "Cargando nombre del usuario")
                }

                Image(
                    painter = painterResource(id = R.drawable.freedom),
                    contentDescription = "Imagen de Presentacion",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .size(120.dp)
                        .clip(CircleShape)
                        .border(2.dp, Color.Black, CircleShape)
                        .clickable { irAPerfilScreen() }
                )

            }

            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 24.dp, start = 24.dp, end = 24.dp)
                    .shadow(3.dp, shape = RoundedCornerShape(20.dp))
                    .background(
                        color = Color.White,
                        shape = RoundedCornerShape(20.dp)
                    )
                    .constrainAs(profile) {
                        top.linkTo(topIng.bottom)
                        bottom.linkTo(topIng.bottom)
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                    }
            ) {

                DashboardButton(
                    icon = R.drawable.video_call,
                    text = "Inventario",
                    onClick = { irAInventario() }
                )
                DashboardButton(
                    icon = R.drawable.notification,
                    text = "Categorías",
                    onClick = { irACategoriaList() }
                )
                DashboardButton(
                    icon = R.drawable.voice_call,
                    text = "Marcas",
                    onClick = { irAMarcasList() }
                )
            }
        }

        Spacer(modifier = Modifier.height(64.dp))


        PromotionCard()



        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center,
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 16.dp, end = 16.dp, top = 24.dp)
        ) {
            DashboardIconButton(
                icon = R.drawable.ic_1,
                text = "Stock Bajo",
                onClick = { irAInventarioStockBajo() }

            )
            DashboardIconButton(
                icon = R.drawable.ic_2,
                text = "Vencimiento Proximo",
                onClick = { irAInventarioFechaExp() }
            )
            DashboardIconButton(
                icon = R.drawable.ic_3,
                text = "Marcas",
                onClick = { irAMarcasList() }
            )
            DashboardIconButton(
                icon = R.drawable.ic_4,
                text = "Reportes",
                onClick = { /* Acción para reportes */ }
            )
        }


    }
}


@Composable
fun DashboardButton(
    @DrawableRes icon: Int,
    text: String,
    onClick: () -> Unit,
) {
    Column(
        modifier = Modifier
            .padding(12.dp)
            .height(90.dp)
            .width(90.dp)
            .background(
                color = Color(android.graphics.Color.parseColor("#b6c2fe")),
                shape = RoundedCornerShape(20.dp)
            )
            .clickable(onClick = onClick),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = painterResource(id = icon),
            contentDescription = "",
            modifier = Modifier.padding(top = 8.dp, bottom = 4.dp)
        )
        Text(
            text = text,
            fontSize = 14.sp,
            fontWeight = FontWeight.Bold,
            fontStyle = FontStyle.Italic,
            color = Color(android.graphics.Color.parseColor("#5e3bee"))
        )
    }
}

@Composable
fun DashboardIconButton(
    @DrawableRes icon: Int,
    text: String,
    onClick: () -> Unit
) {
    Column(
        modifier = Modifier
            .padding(horizontal = 10.dp)
            .clickable(onClick = onClick),

        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = painterResource(id = icon),
            contentDescription = null,
            modifier = Modifier
                .padding(top = 8.dp, bottom = 4.dp)
                .background(
                    color = Color.White,
                    shape = RoundedCornerShape(10.dp)
                )
                .padding(16.dp)
        )
        Text(
            text = text,
            fontSize = 14.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(top = 8.dp),
            color = Color(android.graphics.Color.parseColor("#2e3d6d"))
        )
    }
}

@Composable
fun PromotionCard() {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(120.dp),
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    Brush.horizontalGradient(
                        colors = listOf(Color(0xFF7787F9), Color(0xFFB6C2FE))
                    )
                )
        ) {
            Row(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Bienvenido a Inventory BR",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                )
                Image(
                    painter = painterResource(id = R.drawable.logo_inventory),
                    contentDescription = "Logo",
                    modifier = Modifier.size(80.dp)
                )
            }
        }
    }
}