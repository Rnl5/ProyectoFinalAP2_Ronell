package com.edu.ucne.proyectofinalap2_ronell.presentation.Home

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import com.edu.ucne.proyectofinalap2_ronell.R

@OptIn(ExperimentalMaterial3Api::class)
@Preview
@Composable
fun Dashboard() {
    Column(
        modifier = Modifier
            .fillMaxHeight()
            .fillMaxWidth()
            .background(color = Color(android.graphics.Color.parseColor("#eeeefb"))),
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
                        text = "Bienvenido",
                        color = Color.White,
                        fontSize = 18.sp,
                    )

                    Text(
                        text = "Ronell J",
                        color = Color.White,
                        fontSize = 22.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(top = 14.dp)
                    )
                }

                Image(
                    imageVector = Icons.Default.AccountCircle,
                    contentDescription = "Imagen de presentacion",
                    modifier = Modifier
                        .width(100.dp)
                        .height(100.dp)
                        .clickable { }
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
                Column(
                    modifier = Modifier
                        .padding(top = 12.dp, bottom = 12.dp, end = 12.dp)
                        .height(90.dp)
                        .width(90.dp)
                        .background(
                            color = Color(android.graphics.Color.parseColor("#b6c2fe")),
                            shape = RoundedCornerShape(20.dp)
                        ),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.video_call),
                        contentDescription = "",
                        modifier = Modifier.padding(top = 8.dp, bottom = 4.dp)
                    )
                    Text(
                        text = "Inventario",
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Bold,
                        fontStyle = FontStyle.Italic,
                        color = Color(android.graphics.Color.parseColor("#5e3bee"))
                    )

                }


                Column(
                    modifier = Modifier
                        .padding(top = 12.dp, bottom = 12.dp, end = 8.dp, start = 8.dp)
                        .height(90.dp)
                        .width(90.dp)
                        .background(
                            color = Color(android.graphics.Color.parseColor("#b6c2fe")),
                            shape = RoundedCornerShape(20.dp)
                        ),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.notification),
                        contentDescription = "",
                        modifier = Modifier.padding(top = 8.dp, bottom = 4.dp)
                    )
                    Text(
                        text = "Notificacion",
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Bold,
                        fontStyle = FontStyle.Italic,
                        color = Color(android.graphics.Color.parseColor("#5e3bee"))
                    )

                }



                Column(
                    modifier = Modifier
                        .padding(top = 12.dp, bottom = 12.dp, start = 8.dp)
                        .height(90.dp)
                        .width(90.dp)
                        .background(
                            color = Color(android.graphics.Color.parseColor("#b6c2fe")),
                            shape = RoundedCornerShape(20.dp)
                        ),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.voice_call),
                        contentDescription = "",
                        modifier = Modifier.padding(top = 8.dp, bottom = 4.dp)
                    )
                    Text(
                        text = "Categorias",
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Bold,
                        fontStyle = FontStyle.Italic,
                        color = Color(android.graphics.Color.parseColor("#5e3bee"))
                    )
                }
            }

        }

        var text by rememberSaveable { mutableStateOf("") }
        TextField(
            value = text, onValueChange = { text = it },
            label = { Text("Buscando ...") },
            trailingIcon = {
                Image(
                    painter = painterResource(id = R.drawable.search_icon),
                    contentDescription = "",
                    modifier = Modifier
                        .size(43.dp)
                        .padding(end = 6.dp)
                )
            },
            shape = RoundedCornerShape(50.dp),
            colors = TextFieldDefaults.outlinedTextFieldColors(
//                backgroundColor = Color.White,
                focusedBorderColor = Color.Transparent,
                unfocusedBorderColor = Color.Transparent,
                focusedTextColor = Color(android.graphics.Color.parseColor("#5e5e5e")),
                unfocusedLabelColor = Color(android.graphics.Color.parseColor("#5e5e5e"))
            ),
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 24.dp, end = 24.dp, start = 24.dp)
                .shadow(3.dp, shape = RoundedCornerShape(50.dp))
                .background(Color.White, CircleShape)
        )

        ConstraintLayout(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 24.dp, end = 24.dp, start = 24.dp)
                .shadow(3.dp, shape = RoundedCornerShape(25.dp))
                .height(145.dp)
                .background(
                    brush = Brush.horizontalGradient(
                        colors = listOf(
                            Color(android.graphics.Color.parseColor("#7787f9")),
                            Color(android.graphics.Color.parseColor("#b6c2fe")),
                        )
                    ), shape = RoundedCornerShape(25.dp)
                )
        ) {
            val (img, text1, text2) = createRefs()

            Image(
                modifier = Modifier.constrainAs(img) {
                    top.linkTo(parent.top)
                    end.linkTo(parent.end)
                    bottom.linkTo(parent.bottom)
                },
                painter = painterResource(id = R.drawable.logo),
                contentDescription = ""
            )
//            Text(text = "To Get Unlimited",
//                fontSize = 18.sp,
//                fontWeight = FontWeight.Bold,
//                color = Color.White,
//                modifier = Modifier
//                    .padding(top = 32.dp)
//                    .constrainAs(text1) {
//                        top.linkTo(parent.top)
//                        end.linkTo(img.start)
//                        start.linkTo(parent.start)
//                    }
//            )



            Text(text = "Actualiza tu cuenta",
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White,
                modifier = Modifier
                    .padding(top = 32.dp)
                    .constrainAs(text2) {
                        top.linkTo(text1.bottom)
                        end.linkTo(text1.end)
                        start.linkTo(text1.start)
                    }
            )
        }


        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center,
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 16.dp, end = 16.dp, top = 24.dp)
        ) {
            Column(
                modifier = Modifier.weight(0.25f),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Image(
                    painter = painterResource(id = R.drawable.ic_1),
                    contentDescription = null,
                    modifier = Modifier
                        .padding(top = 8.dp, bottom = 4.dp)
                        .background(
                            color = Color.White,
                            shape = RoundedCornerShape(10.dp)
                        )
                        .padding(16.dp)
                    )
                Text(text = "Productos",
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(top = 8.dp),
                    color = Color(android.graphics.Color.parseColor("#2e3d6d")))

            }


            Column(
                modifier = Modifier.weight(0.25f),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Image(
                    painter = painterResource(id = R.drawable.ic_2),
                    contentDescription = null,
                    modifier = Modifier
                        .padding(top = 8.dp, bottom = 4.dp)
                        .background(
                            color = Color.White,
                            shape = RoundedCornerShape(10.dp)
                        )
                        .padding(16.dp)
                )
                Text(text = "Categorias",
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(top = 8.dp),
                    color = Color(android.graphics.Color.parseColor("#2e3d6d")))

            }




            Column(
                modifier = Modifier.weight(0.25f),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Image(
                    painter = painterResource(id = R.drawable.ic_3),
                    contentDescription = null,
                    modifier = Modifier
                        .padding(top = 8.dp, bottom = 4.dp)
                        .background(
                            color = Color.White,
                            shape = RoundedCornerShape(10.dp)
                        )
                        .padding(16.dp)
                )
                Text(text = "Marcas",
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(top = 8.dp),
                    color = Color(android.graphics.Color.parseColor("#2e3d6d")))

            }


            Column(
                modifier = Modifier.weight(0.25f),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Image(
                    painter = painterResource(id = R.drawable.ic_4),
                    contentDescription = null,
                    modifier = Modifier
                        .padding(top = 8.dp, bottom = 4.dp)
                        .background(
                            color = Color.White,
                            shape = RoundedCornerShape(10.dp)
                        )
                        .padding(16.dp)
                )
                Text(text = "Reportes",
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(top = 8.dp),
                    color = Color(android.graphics.Color.parseColor("#2e3d6d")))
            }

        }

//        Row(
//            verticalAlignment = Alignment.CenterVertically,
//            horizontalArrangement = Arrangement.Center,
//            modifier = Modifier
//                .fillMaxWidth()
//                .padding(start = 16.dp, end = 16.dp, top = 24.dp)
//        ) {
//            Column(
//                modifier = Modifier.weight(0.25f),
//                horizontalAlignment = Alignment.CenterHorizontally
//            ) {
//                Image(
//                    painter = painterResource(id = R.drawable.ic_5),
//                    contentDescription = null,
//                    modifier = Modifier
//                        .padding(top = 8.dp, bottom = 4.dp)
//                        .background(
//                            color = Color.White,
//                            shape = RoundedCornerShape(10.dp)
//                        )
//                        .padding(16.dp)
//                )
//                Text(text = "Calendario",
//                    fontSize = 14.sp,
//                    fontWeight = FontWeight.Bold,
//                    modifier = Modifier.padding(top = 8.dp),
//                    color = Color(android.graphics.Color.parseColor("#2e3d6d")))
//
//            }
//
//
//            Column(
//                modifier = Modifier.weight(0.25f),
//                horizontalAlignment = Alignment.CenterHorizontally
//            ) {
//                Image(
//                    painter = painterResource(id = R.drawable.ic_6),
//                    contentDescription = null,
//                    modifier = Modifier
//                        .padding(top = 8.dp, bottom = 4.dp)
//                        .background(
//                            color = Color.White,
//                            shape = RoundedCornerShape(10.dp)
//                        )
//                        .padding(16.dp)
//                )
//                Text(text = "Tips",
//                    fontSize = 14.sp,
//                    fontWeight = FontWeight.Bold,
//                    modifier = Modifier.padding(top = 8.dp),
//                    color = Color(android.graphics.Color.parseColor("#2e3d6d")))
//
//            }
//
//
//
//
//            Column(
//                modifier = Modifier.weight(0.25f),
//                horizontalAlignment = Alignment.CenterHorizontally
//            ) {
//                Image(
//                    painter = painterResource(id = R.drawable.ic_7),
//                    contentDescription = null,
//                    modifier = Modifier
//                        .padding(top = 8.dp, bottom = 4.dp)
//                        .background(
//                            color = Color.White,
//                            shape = RoundedCornerShape(10.dp)
//                        )
//                        .padding(16.dp)
//                )
//                Text(text = "Configuracion",
//                    fontSize = 14.sp,
//                    fontWeight = FontWeight.Bold,
//                    modifier = Modifier.padding(top = 8.dp),
//                    color = Color(android.graphics.Color.parseColor("#2e3d6d")))
//
//            }
//
//
//            Column(
//                modifier = Modifier.weight(0.25f),
//                horizontalAlignment = Alignment.CenterHorizontally
//            ) {
//                Image(
//                    painter = painterResource(id = R.drawable.ic_8),
//                    contentDescription = null,
//                    modifier = Modifier
//                        .padding(top = 8.dp, bottom = 4.dp)
//                        .background(
//                            color = Color.White,
//                            shape = RoundedCornerShape(10.dp)
//                        )
//                        .padding(16.dp)
//                )
//                Text(text = "Mas",
//                    fontSize = 14.sp,
//                    fontWeight = FontWeight.Bold,
//                    modifier = Modifier.padding(top = 8.dp),
//                    color = Color(android.graphics.Color.parseColor("#2e3d6d")))
//            }
//
//        }


    }
}