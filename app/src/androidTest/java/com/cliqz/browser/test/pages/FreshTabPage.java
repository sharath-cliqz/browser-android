package com.cliqz.browser.test.pages;

import android.support.test.uiautomator.By;
import android.support.test.uiautomator.BySelector;
import android.support.test.uiautomator.Direction;
import android.support.test.uiautomator.UiObject2;

import com.cliqz.browser.test.CliqzRunner;
import com.cliqz.browser.test.driver.NativeDriver;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Copyright © Cliqz 2019
 */
@RunWith(CliqzRunner.class)
public class FreshTabPage extends BasePage {

    // *** Test Data and/or Requirements ***
    private static final String URL_BAR_TEXT = "Search"; // Assert Equals
    private static final String EMPTY_TOP_SITE_TEXT = "Your most visited sites will appear here"; // Assert Equals

    // *** Locators ***
    private static final BySelector DISMISS_BUTTON = By.res(
            NativeDriver.APP_PACKAGE + ":id/rn_redbox_dismiss_button");
    private static final BySelector CANCEL_BUTTON = By.res(
            NativeDriver.APP_PACKAGE + ":id/button2");
    private static final BySelector TOP_BAR = By.res(
            NativeDriver.APP_PACKAGE + ":id/toolbar_container");
    private static final BySelector ADDRESS_BAR = By.res(
            NativeDriver.APP_PACKAGE + ":id/title_bar");
    private static final BySelector TABS_BUTTON = By.res(
            NativeDriver.APP_PACKAGE + ":id/open_tabs_count");
    private static final BySelector MENU_BUTTON = By.res(
            NativeDriver.APP_PACKAGE + ":id/overflow_menu_icon");
    private static final BySelector URL_BAR_EDIT = By.res(
            NativeDriver.APP_PACKAGE + ":id/search_edit_text");
    private static final BySelector FRESH_TAB_VIEW = By.res(
            NativeDriver.APP_PACKAGE + ":id/container");
    private static final BySelector TOP_SITES_GRID = By.res(
            NativeDriver.APP_PACKAGE + ":id/topsites_grid");
    private static final BySelector EMPTY_TOP_SITES_ELEMENT = By.res(
            NativeDriver.APP_PACKAGE + ":id/topsites_header");
    private static final BySelector NEWS_VIEW = By.res(
            NativeDriver.APP_PACKAGE + ":id/news_widget");
    private static final BySelector NEWS_SECTION_TOGGLE = By.res(
            NativeDriver.APP_PACKAGE + ":id/news_label"); // Click on Right Edge
    private static final BySelector NEWS_LIST_VIEW = By.res(
            NativeDriver.APP_PACKAGE + ":id/topnews_list");
    private static final BySelector NEWS_ITEM_ICON = By.res(
            NativeDriver.APP_PACKAGE + ":id/icon_view");
    private static final BySelector NEWS_ITEM_TITLE = By.res(
            NativeDriver.APP_PACKAGE + ":id/title_view");
    private static final BySelector NEWS_ITEM_URL = By.res(
            NativeDriver.APP_PACKAGE + ":id/url_view");

    // *** Override Class Methods ***
    @Override
    @Before
    public void setUp() throws Exception {
        super.clearData();
        super.setUp();
        try {
            getDismissButton().click();
            getCancelButton().click();
        } catch(Error e) {
            driver.logWarn(e.toString());
        }
    }

    // *** Page Methods ***

    private UiObject2 getDismissButton() {
        return driver.getElement(DISMISS_BUTTON, 2000);
    }

    private UiObject2 getCancelButton() {
        return driver.getElement(CANCEL_BUTTON, 1000);
    }

    private UiObject2 getTopBar() {
        return driver.getElement(TOP_BAR);
    }

    private UiObject2 getAddressBar() {
        return driver.getElement(getTopBar(), ADDRESS_BAR);
    }

    private UiObject2 getTabsButton() {
        return driver.getElement(getTopBar(), TABS_BUTTON);
    }

    private String getTabsCount() {
        return getTabsButton().getText();
    }

    private UiObject2 getMenuButton() {
        return driver.getElement(getTopBar(), MENU_BUTTON);
    }

    private UiObject2 getUrlBarEdit() {
        return driver.getElement(URL_BAR_EDIT);
    }

    private UiObject2 getFreshTabView() {
        return driver.getElement(FRESH_TAB_VIEW);
    }

    private UiObject2 getTopSitesEmptyText() {
        return driver.getElement(getFreshTabView(), EMPTY_TOP_SITES_ELEMENT);
    }

    private UiObject2 getNewsSection() {
        return driver.getElement(getFreshTabView(), NEWS_VIEW);
    }

    private UiObject2 getNewsToggleButton() {
        return driver.getElement(getNewsSection(), NEWS_SECTION_TOGGLE);
    }

    private UiObject2 getNewsListView() {
        return driver.getElement(getNewsSection(), NEWS_LIST_VIEW);
    }

    private List<UiObject2> getNewsListElements() {
        return getNewsListView().getChildren();
    }

    private UiObject2 getNewsIcon(UiObject2 newsItem) {
        return driver.getElement(newsItem, NEWS_ITEM_ICON, 500);
    }

    private UiObject2 getNewsTitle(UiObject2 newsItem) {
        return driver.getElement(newsItem, NEWS_ITEM_TITLE, 500);
    }

    private UiObject2 getNewsUrl(UiObject2 newsItem) {
        return driver.getElement(newsItem, NEWS_ITEM_URL, 500);
    }

    private List<Map<String, String>> getNewsListData() {
        List<Map<String, String>> newsData = new ArrayList<>();
        boolean found = true;
        int count = 0;
        while(found && (count<3)) {
            found = false;
            List<UiObject2> newsElemList = getNewsListElements();
            if(newsElemList.size() == 0){
                throw new Error("News Section is Empty !");
            }
            for (int i = 0; i < newsElemList.size(); i++) {
                Map<String, String> data = new HashMap<>();
                try {
                    driver.log(getNewsTitle(newsElemList.get(i)).getText());
                    data.put("title", getNewsTitle(newsElemList.get(i)).getText());
                    data.put("url", getNewsUrl(newsElemList.get(i)).getText());
                    data.put("icon", (getNewsIcon(newsElemList.get(i)) != null) ? "true" : "false");
                    if (!newsData.contains(data)) {
                        newsData.add(data);
                        found = true;
                    } else {
                        i = i + newsData.size() - newsData.indexOf(data);
                    }
                } catch(Error e) {
                    driver.logWarn(e.toString());
                    found = true;
                }
            }
            getNewsSection().swipe(Direction.UP, 0.5f);
            count++;
        }
        getNewsSection().swipe(Direction.DOWN, 1.0f);
        return newsData;
    }

    // *** Test Methods ***
    @Test
    public void testUrlBar() {
        UiObject2 urlBarElem = getUrlBarEdit();
        Assert.assertTrue(
                "Check that the Url Bar exists.",
                driver.exists(URL_BAR_EDIT)
        );
        Assert.assertEquals(
                "Check if the Url Bar Text is correct.",
                URL_BAR_TEXT,
                urlBarElem.getText()
        );
        driver.androidBack();
        final UiObject2 addressBarElem = getAddressBar();
        Assert.assertTrue(
                "Check that the Address Bar, Tabs and Menu Buttons exists.",
                driver.exists(ADDRESS_BAR, TABS_BUTTON, MENU_BUTTON)
        );
        addressBarElem.click();
        Assert.assertFalse(
                "Check that the Address Bar, Tabs and Menu Buttons do not exist.",
                driver.exists(ADDRESS_BAR, TABS_BUTTON, MENU_BUTTON)
        );
        urlBarElem = getUrlBarEdit();
        driver.typeAndWait(urlBarElem, "Hello");
        Assert.assertEquals(
                "Check that the URL Bar Edit Text changes to 'Hello'.",
                "Hello",
                urlBarElem.getText()
        );
        driver.log("Url Bar is working as intended.");
    }

    @Test
    public void testTabsButton() {
        driver.androidBack();
        Assert.assertTrue(
                "Check that the Tabs Button exists.",
                driver.exists(TABS_BUTTON)
        );
        Assert.assertEquals(
                "Check if the Tabs Count is 1.",
                "1",
                getTabsCount()
        );
    }

    @Test
    public void testMenuButton() {
        driver.androidBack();
        Assert.assertTrue(
                "Check that the Menu Button exists.",
                driver.exists(MENU_BUTTON)
        );
    }

    @Test
    public void testTopSitesSectionEmpty() {
        final UiObject2 emptyTextElement = getTopSitesEmptyText();
        Assert.assertNotNull(
                "Check that a Text is shown if Top Sites are empty.",
                emptyTextElement
        );
        Assert.assertEquals(
                "Check that the Text shown for Empty Top Sites is correct.",
                EMPTY_TOP_SITE_TEXT,
                emptyTextElement.getText()
        );
    }

    @Test
    public void testNewsSection() {
        driver.androidBack();
        List<Map<String, String>> newsData = getNewsListData();
        Assert.assertNotNull(
                "Check that the News List is not Null",
                newsData
        );
        for(int i=0; i<newsData.size(); i++){
            driver.log(newsData.get(i).toString());
            Assert.assertEquals(
                    "Check if News No: " + i + " has an icon.",
                    "true",
                    newsData.get(i).get("icon")
            );
            Assert.assertNotNull(
                    "Check if News No: " + i + " has a title.",
                    newsData.get(i).get("title")
            );
            Assert.assertNotNull(
                    "Check if News No: " + i + " has a url.",
                    newsData.get(i).get("url")
            );
        }
        getNewsToggleButton().click();
    }
}
