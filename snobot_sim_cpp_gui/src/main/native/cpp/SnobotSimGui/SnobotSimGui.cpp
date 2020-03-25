
#include "SnobotSimGui/SnobotSimGui.h"
#include "SnobotSim/SensorActuatorRegistry.h"


#include "SnobotSimGui/ModuleWrapperDisplay/AccelerometerWidget.h"
#include "SnobotSimGui/ModuleWrapperDisplay/AnalogInWidget.h"
#include "SnobotSimGui/ModuleWrapperDisplay/AnalogOutWidget.h"
#include "SnobotSimGui/ModuleWrapperDisplay/DigitalIoWidget.h"
#include "SnobotSimGui/ModuleWrapperDisplay/EncoderWidget.h"
#include "SnobotSimGui/ModuleWrapperDisplay/GyroWidget.h"
#include "SnobotSimGui/ModuleWrapperDisplay/RelayWidget.h"
#include "SnobotSimGui/ModuleWrapperDisplay/SolenoidWidget.h"
#include "SnobotSimGui/ModuleWrapperDisplay/SpeedControllerWidget.h"


#include <imgui.h>
#include <imgui_ProggyDotted.h>
#include <imgui_impl_glfw.h>
#include <imgui_impl_opengl3.h>
#include <imgui_internal.h>

#include <GL/gl3w.h>
#include <GLFW/glfw3.h>

#include <iostream>

namespace SnobotSim
{

namespace {
        
void glfw_error_callback(int error, const char *description)
{
	fprintf(stderr, "Glfw Error %d: %s\n", error, description);
}

}

SnobotSimGui::SnobotSimGui() : 
    mRunning(false)
{
    mWidgets.push_back(std::shared_ptr<IWidgetDisplay>(new AccelerometerWidget));
    mWidgets.push_back(std::shared_ptr<IWidgetDisplay>(new AnalogInWidget));
    mWidgets.push_back(std::shared_ptr<IWidgetDisplay>(new AnalogOutWidget));
    mWidgets.push_back(std::shared_ptr<IWidgetDisplay>(new DigitalIoWidget));
    mWidgets.push_back(std::shared_ptr<IWidgetDisplay>(new EncoderWidget));
    mWidgets.push_back(std::shared_ptr<IWidgetDisplay>(new GyroWidget));
    mWidgets.push_back(std::shared_ptr<IWidgetDisplay>(new RelayWidget));
    mWidgets.push_back(std::shared_ptr<IWidgetDisplay>(new SolenoidWidget));
    mWidgets.push_back(std::shared_ptr<IWidgetDisplay>(new SpeedControllerWidget));
}

void SnobotSimGui::RenderLoop()
{
    for(auto widget : mWidgets)
    {
        widget->updateDisplay();
    }
}

void SnobotSimGui::StartThread()
{
    mRunning = true;
  
    std::cout << "Starting GUI thread" << std::endl;

    // Setup window
    glfwSetErrorCallback(glfw_error_callback);
    glfwInitHint(GLFW_JOYSTICK_HAT_BUTTONS, GLFW_FALSE);
    if (!glfwInit()) 
    {
        return;
    }

    // Decide GL+GLSL versions
    #if __APPLE__
    // GL 3.2 + GLSL 150
    const char* glsl_version = "#version 150";
    glfwWindowHint(GLFW_CONTEXT_VERSION_MAJOR, 3);
    glfwWindowHint(GLFW_CONTEXT_VERSION_MINOR, 2);
    glfwWindowHint(GLFW_OPENGL_PROFILE, GLFW_OPENGL_CORE_PROFILE);  // 3.2+ only
    glfwWindowHint(GLFW_OPENGL_FORWARD_COMPAT, GL_TRUE);  // Required on Mac
    glfwWindowHint(GLFW_COCOA_GRAPHICS_SWITCHING, GLFW_TRUE);
    #else
    // GL 3.0 + GLSL 130
    const char* glsl_version = "#version 130";
    glfwWindowHint(GLFW_CONTEXT_VERSION_MAJOR, 3);
    glfwWindowHint(GLFW_CONTEXT_VERSION_MINOR, 0);
    // glfwWindowHint(GLFW_OPENGL_PROFILE, GLFW_OPENGL_CORE_PROFILE);  // 3.2+
    // glfwWindowHint(GLFW_OPENGL_FORWARD_COMPAT, GL_TRUE); // 3.0+
    #endif

	GLFWwindow *window = glfwCreateWindow(1280, 720, "Snobot Sim", NULL, NULL);
	glfwMakeContextCurrent(window);
	glfwSwapInterval(1); // Enable vsync

    // Initialize OpenGL loader
	bool err = gl3wInit() != 0;
	if (err)
	{
		fprintf(stderr, "Failed to initialize OpenGL loader!\n");
		return;
	}

	int screen_width, screen_height;
	glfwGetFramebufferSize(window, &screen_width, &screen_height);
	glViewport(0, 0, screen_width, screen_height);

    // Setup Dear ImGui context
    IMGUI_CHECKVERSION();
    ImGui::CreateContext();
    ImGuiIO& io = ImGui::GetIO();

    // Setup Platform/Renderer bindings
    ImGui_ImplGlfw_InitForOpenGL(window, true);
    ImGui_ImplOpenGL3_Init(glsl_version);
  
    // Setup Dear ImGui style
    ImGui::StyleColorsDark();

    while (!glfwWindowShouldClose(window) && mRunning)
    {
        std::cout << "Running gui loop" << std::endl;
        glfwPollEvents();
        glClearColor(0.45f, 0.55f, 0.60f, 1.00f);
        glClear(GL_COLOR_BUFFER_BIT);

        // feed inputs to dear imgui, start new frame
        ImGui_ImplOpenGL3_NewFrame();
        ImGui_ImplGlfw_NewFrame();
        ImGui::NewFrame();

        // Render our stuff
        RenderLoop();

        // Render dear imgui into screen
        ImGui::Render();
        ImGui_ImplOpenGL3_RenderDrawData(ImGui::GetDrawData());

        int display_w, display_h;
        glfwGetFramebufferSize(window, &display_w, &display_h);
        glViewport(0, 0, display_w, display_h);
        glfwSwapBuffers(window);
    }

    // Cleanup
    ImGui_ImplOpenGL3_Shutdown();
    ImGui_ImplGlfw_Shutdown();
    ImGui::DestroyContext();

	glfwDestroyWindow(window);
	glfwTerminate();
}

void SnobotSimGui::Stop()
{
    mRunning = false;
}

}