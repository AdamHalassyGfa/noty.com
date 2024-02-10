import static com.kms.katalon.core.checkpoint.CheckpointFactory.findCheckpoint
import static com.kms.katalon.core.testcase.TestCaseFactory.findTestCase
import static com.kms.katalon.core.testdata.TestDataFactory.findTestData
import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject
import static com.kms.katalon.core.testobject.ObjectRepository.findWindowsObject
import com.kms.katalon.core.checkpoint.Checkpoint as Checkpoint
import com.kms.katalon.core.cucumber.keyword.CucumberBuiltinKeywords as CucumberKW
import com.kms.katalon.core.mobile.keyword.MobileBuiltInKeywords as Mobile
import com.kms.katalon.core.model.FailureHandling as FailureHandling
import com.kms.katalon.core.testcase.TestCase as TestCase
import com.kms.katalon.core.testdata.TestData as TestData
import com.kms.katalon.core.testng.keyword.TestNGBuiltinKeywords as TestNGKW
import com.kms.katalon.core.testobject.TestObject as TestObject
import com.kms.katalon.core.webservice.keyword.WSBuiltInKeywords as WS
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
import com.kms.katalon.core.windows.keyword.WindowsBuiltinKeywords as Windows
import internal.GlobalVariable as GlobalVariable
import org.openqa.selenium.Keys as Keys

WebUI.openBrowser('')

WebUI.navigateToUrl('http://me.ahala.org:8080/home/landing')

WebUI.click(findTestObject('Object Repository/Page_ComNotyWebui/a_Lets go'))

WebUI.setText(findTestObject('Object Repository/Page_ComNotyWebui/input_Sign up_email'), GlobalVariable.user_name)

WebUI.setEncryptedText(findTestObject('Object Repository/Page_ComNotyWebui/input_Email_password'), '4DY6ru+/ER23gOmv0IKEmw==')

WebUI.click(findTestObject('Object Repository/Page_ComNotyWebui/button_Sign in'))

WebUI.verifyElementPresent(findTestObject('Object Repository/Page_ComNotyWebui/div_User not found'), 5, FailureHandling.STOP_ON_FAILURE)

WebUI.closeBrowser()

