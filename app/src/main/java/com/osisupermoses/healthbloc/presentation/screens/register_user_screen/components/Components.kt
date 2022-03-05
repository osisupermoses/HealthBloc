package com.osisupermoses.healthbloc.presentation.screens.register_user_screen.components

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.ClickableText
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.osisupermoses.healthbloc.R
import com.osisupermoses.healthbloc.ui.theme.spacing


@Composable
fun HealthBlocTopAppBar(
    title: String = "",
    icon: ImageVector? = null,
    navController: NavController,
    onBackArrowClicked: () -> Unit = {}
) {

    TopAppBar(
        title = {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_baseline_star_24),
                    contentDescription = "star",
                    tint = Color(0xFFFFD700)
                )
                Spacer(modifier = Modifier.width(MaterialTheme.spacing.small))
                Text(
                    text = stringResource(R.string.txt_topBar),
                    style = MaterialTheme.typography.h6,
                    fontSize = 14.sp,
                    color = Color.LightGray
                )
            }
            Icon(imageVector = Icons.Default.ArrowBack,
                contentDescription = "Arrow back",
                tint = Color.Black.copy(alpha = 0.7f),
                modifier = Modifier.clickable { onBackArrowClicked.invoke() }
            )
        },
//        backgroundColor = Color.Transparent
    )
}

@SuppressLint("UnrememberedMutableState")
@Composable
fun PhoneNumberTextField(
    value: String,
    onCountrySelected: (Country) -> Unit,
    modifier: Modifier = Modifier,
    countriesList: List<Country>,
    mobileCountry: Country,
    onValueChanged: (String) -> Unit = {}
) {


    val mobileCountryState = remember { mutableStateOf(mobileCountry) }

    Column{
        Row(
            modifier = modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = Icons.Default.Phone,
                contentDescription = "PhoneIcon",
                tint = MaterialTheme.colors.onBackground,
                modifier = Modifier.padding(MaterialTheme.spacing.small)
            )
            Text(
                text = stringResource(R.string.txt_phone_num),
                color = Color.LightGray,
                style = MaterialTheme.typography.body2,
                fontWeight = FontWeight.Bold
            )
        }
        OutlinedTextField(
            value = value,
            onValueChange = onValueChanged,
            modifier = modifier.fillMaxWidth(),
            shape = RoundedCornerShape(corner = CornerSize(5.dp)),
            singleLine = true,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone),
            placeholder = {
                Text(
                    text = stringResource(R.string.txt_phone_num_placeholder),
                    fontSize = 15.sp,
                    style = MaterialTheme.typography.body2,
                    textAlign = TextAlign.Start,
                    modifier = Modifier.padding(horizontal = 5.dp)
                )
            },
            leadingIcon = {
                mobileCountryState.value?.let {
                    CountryPickerView(
                        countries = countriesList,
                        selectedCountry = it,
                        onSelection = onCountrySelected
                    )
                }
            },
            visualTransformation = PhoneNumberVisualTransformation()
        )
    }
}

@Composable
fun CountryPickerView(
    selectedCountry: Country,
    onSelection: (Country) -> Unit,
    countries: List<Country>
) {
    var showDialog by remember { mutableStateOf(false) }
    Row(
        modifier = Modifier.clickable { showDialog = true },
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            modifier = Modifier
                .padding(start = 20.dp, end = 5.dp),
            text = "${getFlagEmojiFor(selectedCountry.nameCode)} + ${selectedCountry.code}"
        )
        Icon(
            imageVector = Icons.Default.ArrowDropDown,
            contentDescription = "See more countries"
        )
    }
    if (showDialog)
        CountryCodePickerDialog(countries, onSelection) {
            showDialog = false
        }
}

@Composable
fun CountryCodePickerDialog(
    countries: List<Country>,
    onSelection: (Country) -> Unit,
    dismiss: () -> Unit,
) {
    Dialog(onDismissRequest = dismiss) {
        Box {
            LazyColumn(
                Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 30.dp, vertical = 40.dp)
                    .background(shape = RoundedCornerShape(20.dp), color = Color.White)
            ) {
                for (country in countries) {
                    item {
                        Text(
                            modifier = Modifier
                                .clickable {
                                    onSelection(country)
                                    dismiss()
                                }
                                .fillMaxWidth()
                                .padding(10.dp),
                            text = "${getFlagEmojiFor(country.nameCode)} ${country.fullName}"
                        )
                    }
                }
            }
        }
    }
}


@Composable
fun TextField(
    modifier: Modifier = Modifier,
    value: String,
    enabled: Boolean = true,
    isSingleLine: Boolean = true,
    placeholderText: String = "",
    icon: ImageVector? = null,
    text: String = "",
    keyboardType: KeyboardType = KeyboardType.Text,
    imeAction: ImeAction = ImeAction.Next,
    onAction: KeyboardActions = KeyboardActions.Default,
    onValueChanged: (String) -> Unit = {}
) {
    Column {
        Row(
            modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            if (icon != null) {
                Icon(
                    imageVector = icon,
                    contentDescription = "Icon",
                    tint = MaterialTheme.colors.onBackground,
                    modifier = Modifier.padding(MaterialTheme.spacing.small)
                )
            }
            Text(
                text = text,
                style = MaterialTheme.typography.body2,
                color = Color.LightGray,
                fontWeight = FontWeight.Bold
            )
        }
        OutlinedTextField(
            value = value,
            onValueChange = onValueChanged,
            modifier = modifier.fillMaxWidth(),
            shape = RoundedCornerShape(corner = CornerSize(5.dp)),
            singleLine = isSingleLine,
            textStyle = TextStyle(fontSize = 18.sp, color = MaterialTheme.colors.onBackground),
            enabled = enabled,
            keyboardOptions = KeyboardOptions(
                keyboardType = keyboardType,
                imeAction = imeAction
            ),
            keyboardActions = onAction,
            placeholder = {
                Text(
                    text = placeholderText,
                    fontSize = 15.sp,
                    style = MaterialTheme.typography.body2,
                    textAlign = TextAlign.Start,
                    modifier = Modifier.padding(horizontal = 5.dp)
                )
            }
        )
    }
}

@Composable
fun EmailInput(
    modifier: Modifier = Modifier,
    email: String,
    enabled: Boolean = true,
    isSingleLine: Boolean = true,
    imeAction: ImeAction = ImeAction.Next,
    onAction: KeyboardActions = KeyboardActions.Default,
    onValueChanged: (String) -> Unit = {}
) {
    Column {
        Row(
            modifier = modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = Icons.Default.Email,
                contentDescription = "Email",
                tint = MaterialTheme.colors.onBackground,
                modifier = Modifier.padding(MaterialTheme.spacing.small)
            )
            Text(
                text = stringResource(R.string.txt_email),
                style = MaterialTheme.typography.body2,
                color = Color.LightGray,
                fontWeight = FontWeight.Bold
            )
        }
        TextField(
            value = email,
            enabled = enabled,
            keyboardType = KeyboardType.Email,
            imeAction = imeAction,
            isSingleLine = isSingleLine,
            onAction = onAction,
            placeholderText = stringResource(R.string.txt_email_placeholder),
            onValueChanged = onValueChanged
        )
    }
}

@Composable
fun PasswordInput(
    modifier: Modifier,
    password: String,
    enabled: Boolean = true,
    imeAction: ImeAction = ImeAction.Done,
    onAction: KeyboardActions = KeyboardActions.Default,
    onValueChanged: (String) -> Unit = {}
) {
    var isPasswordVisible by remember { mutableStateOf(false) }

    Column {
        Row(
            modifier = modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = Icons.Default.Lock,
                contentDescription = "Lock",
                tint = MaterialTheme.colors.onBackground,
                modifier = Modifier.padding(MaterialTheme.spacing.small)
            )
            Text(
                text = stringResource(R.string.txt_password),
                style = MaterialTheme.typography.subtitle2,
                color = Color.LightGray,
                fontWeight = FontWeight.Bold
            )
        }
        OutlinedTextField(
            value = password,
            onValueChange = onValueChanged,
            singleLine = true,
            textStyle = TextStyle(
                fontSize = 18.sp,
                color = MaterialTheme.colors.onBackground
            ),
            modifier = modifier.fillMaxWidth(),
            enabled = enabled,
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Password,
                imeAction = imeAction
            ),
            placeholder = {
                Text(
                    text = stringResource(R.string.txt_password_placeholder),
                    fontSize = 15.sp,
                    style = MaterialTheme.typography.body2,
                    textAlign = TextAlign.Start,
                    modifier = Modifier.padding(horizontal = 5.dp)
                )
            },
            visualTransformation =
                if (isPasswordVisible) VisualTransformation.None
                else PasswordVisualTransformation(),
            leadingIcon = {
                IconButton(
                    onClick = {
                        isPasswordVisible = !isPasswordVisible
                    }
                ) {
                    Icon(
                        imageVector = if (isPasswordVisible)
                            Icons.Filled.Visibility
                        else
                            Icons.Filled.VisibilityOff,
                        contentDescription = "Password Visibility"
                    )
                }
            },
            keyboardActions = onAction
        )
    }
}

@Composable
fun CheckedBoxWithText(
    isChecked: Boolean,
    enabled: Boolean = true,
    onTextClicked: () -> Unit = {},
    onCheckedChanged: (Boolean) -> Unit = {}
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Checkbox(
            checked = isChecked,
            onCheckedChange = onCheckedChanged,
            enabled = enabled,
            colors = CheckboxDefaults.colors(Color.DarkGray)
        )
//        Text(
//            text = stringResource(R.string.txt_policy_normal),
//            style = MaterialTheme.typography.subtitle2
//        )
//        Text(
//            text = stringResource(R.string.txt_policy_link),
//            style = MaterialTheme.typography.subtitle2,
//            color = Color.Blue,
//            modifier = Modifier.clickable { onTextClicked.invoke() }
//        )
        AnnotatedClickableText()
    }
}

@Composable
fun AnnotatedClickableText() {
    val annotatedText = buildAnnotatedString {
        append(stringResource(R.string.txt_policy_normal))

        // We attach this *URL* annotation to the following content
        // until `pop()` is called
        pushStringAnnotation(
            tag = "URL",
            annotation = "https://developer.android.com"
        )
        withStyle(
            style = SpanStyle(
                color = Color.Blue,
                fontWeight = FontWeight.Normal,
                fontFamily = FontFamily.SansSerif,
            )
        ) {
            append(" " + stringResource(R.string.txt_policy_link))
        }
        pop()
    }
    ClickableText(
        text = annotatedText,
        onClick = { offset ->
            // We check if there is an *URL* annotation attached to the text
            // at the clicked position
            annotatedText.getStringAnnotations(
                tag = "URL",
                start = offset,
                end = offset
            )
                .firstOrNull()?.let { annotation ->
                    // If yes, we log its value
                    Log.d("Clicked URL", annotation.item)
                }
        }
    )
}
@Composable
fun Button(
    modifier: Modifier = Modifier,
    btnColor: ButtonColors,
    textColor: Color,
    enabled: Boolean = true,
    text: String,
    onButtonClicked: () -> Unit,
) {
    Button(
        onClick = onButtonClicked,
        colors = btnColor,
        modifier = modifier
            .fillMaxWidth()
            .padding(MaterialTheme.spacing.large)
            .height(40.dp),
        shape = RoundedCornerShape(corner = CornerSize(8.dp)),
        enabled = enabled
    ) {
        Text(
            text = text,
            color = textColor,
            style = MaterialTheme.typography.button
        )
    }

}

@Composable
fun Toast(text: String) {
    android.widget.Toast.makeText(
        LocalContext.current,
        text, // stringResource(R.string.txt_toast)
        android.widget.Toast.LENGTH_LONG
    ).show()
}

@Composable
@Preview
fun ComponentsPreview() {
    val navController = rememberNavController()
//    PhoneNumberTextField(
//        modifier = Modifier.fillMaxWidth(),
//        placeholderText = "Mobile Number")
//    TextField(placeholderText = "Number Text")
}